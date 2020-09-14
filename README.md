# auth-service
Authentication and authorisation service.\
User credentails are hashed with random salt and pepper before storing in database.\
Login validates credentals and provide jwt for authorisation.
Access token and refresh token are provided on login. Access token is short lived while refresh token have longer life. On access token expiry new tokens can be regenereted using refresh token.
