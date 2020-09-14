# auth-service
Authentication and authorisation service.\
User credentails are hashed with random salt and pepper before storing in database.\
Access token and refresh token are provided on successfull login. Access token has short life while refresh token has longer life. On access token expiry new tokens can be regenereted using refresh token.
