const net = require('net')

const SEPARATOR = ";"

const client = new Client({ip: "localhost", port: 8999})

function Client({ port, ip }) {
    const self = this

    let clientSocket
    const outQueue = []

    connectTo(port, ip)
    
    function connectTo(port, ip) {
        const client = new net.Socket()
        clientSocket = client

        client.connect({ port, ip }, () => console.log(`\tConnecting...`) )

        client.on('connect', () => {
            console.log(`\tConnected`)
            flushOutQueue()
        })

        client.on('data', message => {
            String(message)
                    .split(SEPARATOR)
                    .map( string => string.trim() )
                    .filter( string => string.length !== 0  )
                    .map( JSON.parse )
                    .forEach( message => console.log(message) )
        })
        
        client.on('close', () =>  console.log(`\tConnection closed`) )
        client.on('error', () => console.log(`\tConnection error`) )
    }

    this.send = function(message) {
        if(!clientSocket.connecting)
            clientSocket.write(SEPARATOR +message +SEPARATOR)
        else {
            console.log(`\tSocket not created, message added to queue`)
            outQueue.push(message)
        }
    }

    this.finish = function() {
        if(clientSocket.connecting)
            clientSocket.on('connect', clientSocket.end )
        else
            clientSocket.end()
    }

    function flushOutQueue() {
        while(outQueue.length !== 0) {
            const data = outQueue.shift()
            self.send(data)
        }
    }
}



module.exports=client;