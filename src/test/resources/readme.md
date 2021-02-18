#generar la clave publica y privada

keytool -genkeypair -alias jwt -keyalg RSA -keypass changeme -keystore jwt.jks -storepass changeme

#se exporta la publica a un fichero
keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey

# se copia el contenido de -----BEGIN PUBLIC KEY----- en un fichero public.key