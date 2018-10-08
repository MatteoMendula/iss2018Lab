package it.unibo.coap.examples;

import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.network.Exchange.Origin;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.proxy.ProxyCoapResolver;
import org.eclipse.californium.proxy.ProxyHttpServer;
import org.eclipse.californium.proxy.RequestHandler;
import org.eclipse.californium.proxy.resources.ProxyCacheResource;
import org.eclipse.californium.proxy.resources.StatsResource;

 

public class MyProxyHttpServer  { //extends ProxyHttpServer

	private final static Logger LOGGER = Logger.getLogger(ProxyHttpServer.class.getCanonicalName());
	
	private static final String PROXY_COAP_CLIENT = "proxy/coapClient";
	private static final String PROXY_HTTP_CLIENT = "proxy/httpClient";

	private final ProxyCacheResource cacheResource = new ProxyCacheResource(true);
	private final StatsResource statsResource = new StatsResource(cacheResource);
	
	private ProxyCoapResolver proxyCoapResolver;
	private MyHttpStack httpStack;

	/**
	 * Instantiates a new proxy endpoint from the default ports.
	 * 
	 * @throws SocketException
	 *             the socket exception
	 */
	public MyProxyHttpServer(CoapServer server) throws IOException {
		this(NetworkConfig.getStandard().getInt(NetworkConfig.Keys.HTTP_PORT));
	}

	/**
	 * Instantiates a new proxy endpoint.
	 * 
	 * @param httpPort
	 *            the http port
	 * @throws IOException
	 *             the socket exception
	 */
	public MyProxyHttpServer(int httpPort) throws IOException {
		System.out.println("MyProxyHttpServer creting on port=" + httpPort );
		this.httpStack = new MyHttpStack(httpPort);
		this.httpStack.setRequestHandler(new RequestHandler() {
			public void handleRequest(Request request) {
				MyProxyHttpServer.this.handleRequest(request);
			}
		});
	}

	public void handleRequest(final Request request) {
		
		System.out.println("MyProxyHttpServer handles request "+request);
		
		Exchange exchange = new Exchange(request, Origin.REMOTE) {

			@Override
			public void sendAccept() {
				// has no meaning for HTTP: do nothing
			}
			@Override
			public void sendReject() {
				// TODO: close the HTTP connection to signal rejection
			}
			@Override
			public void sendResponse(Response response) {
				// Redirect the response to the HttpStack instead of a normal
				// CoAP endpoint.
				// TODO: When we change endpoint to be an interface, we can
				// redirect the responses a little more elegantly.
				try {
					request.setResponse(response);
					responseProduced(request, response);
					System.out.println("MyProxyEndpoint sends response=" + response );
// 					HttpResponse httpResponse = httpStack.myHttpResponse;
//					 httpResponse.setStatusCode(HttpStatus.SC_OK);
//					 httpResponse.setEntity(new StringEntity("xxxxxxxx"));

 					httpStack.doSendResponse(request, response);
//					httpStack.sendSimpleHttpResponse(request, response);
//					exchange.sendResponse(new Response(ResponseCode.BAD_OPTION));
				} catch (Exception e) {
					LOGGER.log(Level.WARNING, "Exception while responding to Http request", e);
				}
			}
		};
		exchange.setRequest(request);
		
		Response response = null;
		// ignore the request if it is reset or acknowledge
		// check if the proxy-uri is defined
		if (request.getType() != Type.RST && request.getType() != Type.ACK 
				&& request.getOptions().hasProxyUri()) {
			// get the response from the cache
			response = cacheResource.getResponse(request);

				LOGGER.info("Cache returned "+response);

			// update statistics
			statsResource.updateStatistics(request, response != null);
		}

		// check if the response is present in the cache
		if (response != null) {
			// link the retrieved response with the request to set the
			// parameters request-specific (i.e., token, id, etc)
			exchange.sendResponse(response);
			return;
		} else {

			// edit the request to be correctly forwarded if the proxy-uri is
			// set
			if (request.getOptions().hasProxyUri()) {
				try {
					manageProxyUriRequest(request);
					LOGGER.info("after manageProxyUriRequest: "+request);

				} catch (URISyntaxException e) {
					LOGGER.warning(String.format("Proxy-uri malformed: %s", request.getOptions().getProxyUri()));

					exchange.sendResponse(new Response(ResponseCode.BAD_OPTION));
				}
			}

			// handle the request as usual
			proxyCoapResolver.forwardRequest(exchange);
			/*
			 * Martin:
			 * Originally, the request was delivered to the ProxyCoAP2Coap which was at the path
			 * proxy/coapClient or to proxy/httpClient
			 * This approach replaces this implicit fuzzy connection with an explicit
			 * and dynamically changeable one.
			 */
		}
	}

	/**
	 * Manage proxy uri request.
	 * 
	 * @param request
	 *            the request
	 * @throws URISyntaxException
	 *             the uRI syntax exception
	 */
	private void manageProxyUriRequest(Request request) throws URISyntaxException {
		// check which schema is requested
		URI proxyUri = new URI(request.getOptions().getProxyUri());

		// the local resource that will abstract the client part of the
		// proxy
		String clientPath;

		// switch between the schema requested
		if (proxyUri.getScheme() != null && proxyUri.getScheme().matches("^http.*")) {
			// the local resource related to the http client
			clientPath = PROXY_HTTP_CLIENT;
		} else {
			// the local resource related to the http client
			clientPath = PROXY_COAP_CLIENT;
		}

		LOGGER.info("Chose "+clientPath+" as clientPath");

		// set the path in the request to be forwarded correctly
		request.getOptions().setUriPath(clientPath);
		
	}

	protected void responseProduced(Request request, Response response) {
		// check if the proxy-uri is defined
		System.out.println("responseProduced for request=" + request);
		if (request.getOptions().hasProxyUri()) {
				LOGGER.info("Cache response");
			// insert the response in the cache
			cacheResource.cacheResponse(request, response);
		} else {
			System.out.println("Do not cache response");
				LOGGER.info("Do not cache response");
		}
	}

	public ProxyCoapResolver getProxyCoapResolver() {
		return proxyCoapResolver;
	}

	public void setProxyCoapResolver(ProxyCoapResolver proxyCoapResolver) {
		this.proxyCoapResolver = proxyCoapResolver;
	}

}
