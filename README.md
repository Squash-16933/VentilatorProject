# LeagueAir
LeagueAir is an open-source project that aims to create a working ventilator with an easy-to-use, beautiful UI.

## Who are we?
We're the [League of Amazing Programmers](https://jointheleague.org), a non-profit organization teaching coding to kids from 5th-12th grade. Our classes meet year round to prepare kids for the science and technology careers of the 21st century.

## Teams
- Coding
- Mechanical
- Math

## About the ventilator
The brain of the LeagueAir ventilator is a Raspberry Pi running Java and Adafruit, and it will have a [Stepper Motor HAT](https://www.adafruit.com/product/2348) and an AMBU bag held by a 3D-printed plastic strap.

## Installation
1. ```
   git clone https://github.com/Squash-16933/VentilatorProject.git
   mvn spring-boot:run
   ```
2. Start the front end

## Accessing the pi
### Accessing the pi terminal
1. Open command line
2. `ssh pi@<IP> -p <PORT>`
3. Type the password
4. This terminal opens: `pi@raspberrypi:~ $`

### Accessing the server program
1. Access the pi terminal
2. `screen -xS dev-tester` (This may fail if the screen is not named `dev-tester`)
3. It will open the server program
4. To get back to the main terminal, `Ctrl-A` then `Ctrl-D`

### Starting the server
1. Access the pi terminal
2. `screen`
3. `cd ~/Desktop/VentilatorProject`
4. `mvn spring-boot:run`
5. Preferably, name the screen to make it easier to access

### Naming the screen
1. `Ctrl-A`
2. `:sessionname <NAME>` (`<NAME>` preferably `dev-tester`)
## Project structure
TBA

## Further reading
- [**World Health Organization** Coronavirus disease (COVID-19) advice for the public](https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public)
- [**Centers for Disease Control** Coronavirus Disease 2019 (COVID-19)](https://www.cdc.gov/coronavirus/2019-nCoV/index.html)
- [**Our World in Data** Coronavirus Pandemic (COVID-19)](https://ourworldindata.org/coronavirus)
- [**Internet Archive** National Emergency Library](https://archive.org/details/nationalemergencylibrary)
- [**Kurzgesagt** The Coronavirus Explained & What You Should Do](https://www.youtube.com/watch?v=BtN-goy9VOY)

## License
This project is under the [MIT License](https://github.com/Squash-16933/VentilatorProject/blob/master/LICENSE.md).
