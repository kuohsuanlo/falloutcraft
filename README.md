# FalloutCraft--- Powered by [LogoCat](https://mcuuid.net/?q=logocat) 
[![mcfallout](https://i.imgur.com/o6S7V07.png)](https://mcfallout.net)
Jar Download link: [Spigot Page](https://www.spigotmc.org/resources/falloutcraft.20984/)

I am personally a Fallout fan. Why not bringing fallout's hardcore survival mode to minecraft ![bed](https://www.csie.ntu.edu.tw/~b98902055/items/2-0.png)?
This plugin is a minecraft server-side spigot plugin in order to enhance the experience of:
  - Fallout System in Minecraft (Radiation, Fatigue, Thirst, Hunger)
  - Just like fallout! Enjoy your Apocalyptic Survival game.

![](https://www.spigotmc.org/attachments/plugins-png.118236/)

---------

  ### Features : 
  Enhanced survival mechanism : 
  - Fatigue ![bed](https://www.csie.ntu.edu.tw/~b98902055/items/355-0.png)
    1. Fatigue gives you some debuffs depends on how tired you are.
    2. Increase when time passes. 50 to 100(RNG based) per 20mins
    3. Decrease when player lays on bed.
    4. Laying on bed gives you [well rested status] providing you 20% of [damage resistance] potion effect.
   
  - Dehydration ![](https://www.csie.ntu.edu.tw/~b98902055/items/373-0.png)
    1. Dehydration gives you some debuffs depends on how dehydrated you are.
    2. Increase when having certain types of food (Such as eating potato ![](https://www.csie.ntu.edu.tw/~b98902055/items/392-0.png), bread ![](https://www.csie.ntu.edu.tw/~b98902055/items/297-0.png), rotten flesh ![](https://www.csie.ntu.edu.tw/~b98902055/items/367-0.png), and catching on  fire ![fire](https://www.csie.ntu.edu.tw/~b98902055/items/51-0.png).
    3. Decrease when players drink from water bottle or any kind of potions. ![](https://www.csie.ntu.edu.tw/~b98902055/items/373-0.png)

  - Radiation ![](https://www.csie.ntu.edu.tw/~b98902055/items/437-0.png)
    1. Radiation first gives you some mutation buffs like night vision. However when the level increases, the debuffs start to appear.
    2. Increase when players hit by creature, and eating food based on the food's radiation level.(Rotten flesh![](https://www.csie.ntu.edu.tw/~b98902055/items/367-0.png) obviously does more than apples ![](https://www.csie.ntu.edu.tw/~b98902055/items/260-0.png).)
    3. Decrease when players drink [radaway]. ![](https://www.csie.ntu.edu.tw/~b98902055/items/438-0.png)

Item crafting : 
  - Radaway Recipe ![](https://www.csie.ntu.edu.tw/~b98902055/items/438-0.png)
Radaway is the only way to decrease your radiation level other than Golen Apple ![](https://www.csie.ntu.edu.tw/~b98902055/items/322-1.png). Multiple kinds of Radaways are craftable using 3x3 crafting table ![](https://www.csie.ntu.edu.tw/~b98902055/items/58-0.png) or player's 2x2 crafting menu.  
    1. Level 1 Radaway : bone meal ![](https://www.csie.ntu.edu.tw/~b98902055/items/351-15.png) + water bottle ![](https://www.csie.ntu.edu.tw/~b98902055/items/373-0.png)
    2. Level 2 Radaway : bone ![](https://www.csie.ntu.edu.tw/~b98902055/items/352-0.png) + water bottle ![](https://www.csie.ntu.edu.tw/~b98902055/items/373-0.png)
    3. Level 4 Radawat : bone block ![](https://www.csie.ntu.edu.tw/~b98902055/items/216-0.png) + water bottle ![](https://www.csie.ntu.edu.tw/~b98902055/items/373-0.png)

Media Demo:
  - [Image  1](https://imgur.com/jpM8iBX.png)  : Text output (show when player receives changes on one of the survival mechanism)
  - [Image 2](https://i.imgur.com/5PMMPNr.png) : placeholderapi support (show with titlemanager) 

----
### Environment 
This build is compiled and tested on these environments.
* [java] - JVM 1.8
* [spigot] - 1.12.x

### Hard-Dependency
This plugin needs to run with the following plugins with the latest version to work properly:
* [placeholderapi] - making plugin to work with each others parameters or output.

### Soft-Dependency
This plugin run with best experience the following plugins :
* [placeholderapi] - making plugin to work with each others parameters or output.
* [titlemanager] - player could easily know their status by press 'tab' or watching scoreboard 
----
### Installation
1. Drop the plugin jar file in your server folder /plugins/ and run once.
2. After the plugin folder and default config.yml is generated, stop the server.
3. Start to set your own config withing config.yml.

### Configuration setting
1. The messages are all configurable.  You can edit them based on the name tag like [SOMEONE_DIED_BECAUSE_OF_RADIATION]. 
2. Color codes escapes are represented with '§'.
3. Since all of the increase, decrease amount of radiation/dehydration is customisable. I only take one of them as example. 
```sh
thirst_poisonous_potato: '50'
# then value >0, increase the dehydration level, otherwise <0 decreases.
YOUR_RADIATION_0_200_MES: ''
# when radation reaches 200 from 400. Show these texts. Defaultly blank. 
YOUR_RADIATION_201_400_MES: 'Your §cradiation sickness§f : §b*---'
# when radation reaches 201~400. Show these texts.
YOUR_RADIATION_401_600_MES: 'Your §cradiation sickness§f : §a**--'
YOUR_RADIATION_601_800_MES: 'Your §cradiation sickness§f : §e***-'
YOUR_RADIATION_801_999_MES: 'Your §cradiation sickness§f : §c****'
# Trivial.
```
----
### Permission nodes
| nodes | description |default|
| ------ | ------ | ------ |
| falloutcraft.* | Gives access to all fallout commands |
| falloutcraft.fostatus | check falloutcraft status.| true
| falloutcraft.setradiation | permission to use command : /fosetr |op|
| falloutcraft.setdehydration |  permission to use command : /foseth |op|
| falloutcraft.setfatigue |  permission to use command : /fosetf |op|

### Commands
| command |description| required permission |
| ------ | ------ |---|
| /fostatus | check falloutcraft status | true|
| /foupdate | this is a dummy command for debugging, forcing the plugin to update all players potion effect gained from those three survival mechanisms. | op|
| /fosetr [id] [lvl]|  set the player's radiation level |falloutcraft.setradiation|
| /fosetd [id] [lvl]|  set the player's dehydration level |falloutcraft.setdehydration|
| /fosetf [id] [lvl]|  set the player's fatigue level |falloutcraft.setfatigue|

----
### Todos
 - Write MORE Tests
 - Add Night Mode

----
### Development

Want to contribute? Great!
This project is open to everyone as long as it follows the [license]. You could follow these steps to build up the developing environment : 
1. Click [here](https://stackoverflow.com/questions/2061094/importing-maven-project-into-eclipse) for instrctions of importing a maven project.
2. Add the dependent plugin .jar file mentioned above. 
3. Run the project as 'maven install'
4. The built version would be in /$project_name/target/


----
### License

MIT licenses https://opensource.org/licenses/MIT
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [item]: <https://www.csie.ntu.edu.tw/~b98902055/items/>

   [vault]: <https://www.spigotmc.org/resources/vault.41918/>
   [multiverse-core]: <https://www.spigotmc.org/resources/multiverse-core.390/>
   [faction]: <https://www.spigotmc.org/resources/factions.1900/>
   [griefprevention]: <https://www.spigotmc.org/resources/griefprevention.1884/>
   [worldedit]: <https://dev.bukkit.org/projects/worldedit/files/2460562>
   [placeholderapi]: <https://www.spigotmc.org/resources/placeholderapi.6245/>
   [titlemanager]: <https://www.spigotmc.org/resources/titlemanager.1049/>
   [spigot]: <https://spigotmc.org>
   [java]: <https://java.com/zh_TW/>
   [license]: <https://opensource.org/licenses/MIT>

