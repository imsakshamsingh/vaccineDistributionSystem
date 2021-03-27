If you face issues while sending email, make sure you turn on allow less secure apps in your google account
 https://www.google.com/settings/security/lesssecureapps 
 
 
############Authentication############
User is admin
Password is admin (encoded as a bcrypt string)




############Docker Steps############
1) docker build -t vaccine-image .
2) docker run -d -p 27000:27017 --name mongo mongo
3) docker run -p 8080:8080 --name vaccine-image --link=mongo  vaccine-image
