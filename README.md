- In this project, we use the Client-Server pattern. For database design, system design and more detailed info about the project, refer to the folder: **21127119_21127089_21127717 **

## Note on pom.xml

### Adding dependencies

1. Search for dependency: https://central.sonatype.com/ and we get this `Maven POM File` snippet.
  ![img.png](img.png)
2. Copy `<dependency>` block and paste into the <dependencies> section of pom.xml.

### Adding resources (i.g: .png, .jpg, etc)

- `Resources` folder is to store images or other files except java. Every file in this folder can be accessed with absolute path.

E.g: Let's say there is _sample.png_ in `Image` inside of `Resources`, path to _sample.png_ is: `/Image/sample.png`. Remember to include the / before Image.

### Apply changes

- On changing `pom.xml`, to apply changes run: `.\mvnw clean`.

## Running the project

For more detail on how this works, check `pom.xml`.
In **powershell**:

- Run Server:
  `.\mvnw exec:java`
- Run Admin:
  `.\mvnw exec:java@client-admin`
- Run User:
  `.\mvnw exec:java@client-user`
- Run All: `.\mvnw package`

### Output

For simple console output:
Check for lines starting with default-cli (server app) and client-execution (client app)
![Alt text](image.png)
