# WebServer

To create a certificate, run
`keytool -genkey -keystore WebServerKeyStore -keyalg RSA`

To use the certificate, run the WebServer with the following VM options:
`-Djavax.net.ssl.keyStore=WebServerKeyStore -Djavax.net.ssl.keyStorePassword=<password>`
Where `<password>` is the password which you used for the keystore.
