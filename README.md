# Shorty

*Shorty* is a tiny little URL shortener. It maps one URL to another. For persistence an H2 file based database is used. No frontend is provided here. 

# Features
Right now Shorty is pretty simple. You can 
* create mappings using the PUT endpoint
* URL representations (QR code)
* retrieve mappings using the GET endpoint

For more details please see openapi.yaml.

However, the feature list may grow over time.

# Examples
#### Create a mapping
```
$ curl -X PUT --location "https://your.domain.tdl" \
    -H "Content-Type: application/json" \
    -d "{ \"originalUrl\": \"https://long.url.tdl\", \"qr\": true }" \
    -D - \
    --output qr-code.png
```
The shortened URL can be found in the Location header of the response. The response body contains the QR code as a PNG resource.

Set "qr" to "false" if you do not need a QR code.

#### Call a mapping
```
$ curl -v "your short url"
```
This retrieves the original URL and redirects you there.

# Future
* Security considerations
* Better logging
* A lifetime feature for mappings
