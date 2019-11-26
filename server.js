const http = require('http');

const url = "http://localhost:8080";

let server = http.createServer();

server.on('request', function(request, response){
  console.log(request.method);
  console.log(request);
  response.writeHead(200);
  response.end("status=200\ndata=ok");
})

server.listen(8080);