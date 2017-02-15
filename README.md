# falloutcraft
Fallout System in Minecraft (Radiation, Fatigue, Thirst, Hunger)

 
![ScreenShot](https://www.spigotmc.org/attachments/foc_logo-png.123360/)
====Minecraft with Fallout Apocalypse feature ====
Now minecraft can have fatigue, dehydration, radiation level. Just like fallout! Enjoy your Apocalyptic Survival game.

![ScreenShot](https://www.spigotmc.org/attachments/plugins-png.118236/)
https://github.com/kuohsuanlo/falloutcraft
====Official Server **mcfallout.net** ====
A hardcore survival server :D. You can try falloutcraft plugin at here. (zh_TW server)

Complied on Java 8.
Spigot 1.8.x- 1.9 compatible


Mechanism :

====Fatigue====

#Increase when time passes. 50 to 100(RNG based) per 20mins
#Decrease when player lays on bed.(Even during day time. Laying on bed is implemented.)
#Well rested status gives you 20% of damage resistance. Otherwise, Fatigue gives you some debuffs depends on how tired you are.
====Dehydration====

#Increase when having certain types of food, and catching on fire. (Such as eating potato, bread, rotten flesh.)
#Decrease when players drink from water bottle or any kind of potions.
#Dehydration gives you some debuffs depends on how dehydrated you are.
====Radiation====

#Increase when players hit by creature, and eating food based on the food's radiation level.(Rotten flesh obviously does more than apples.)
#Decrease when player drink from water bottle ,golden apple or any kind of potions.;
#Radiation first gives you some mutation buffs like night vision. However when the level increases, the debuffs start to appear.
#(This will be implemented more specifically. Like different potions decreases different amount of radiation. Now, just water and potion :). It will be configurable in the future.)

[​IMG]
[​IMG]
* permissions:
falloutcraft.*:
description: Gives access to all fallout commands

falloutcraft.fostatus:
description: check falloutcraft status. default: true

falloutcraft.setradiation:
description: set a player's radiation level default: op

falloutcraft.setdehydration:
description: set a player's dehydration level default: op

falloutcraft.setfatigue:
description: set a player's fatigue level default: op

* commands:
fostatus:
description: check falloutcraft status. usage: "/fostatus"

foupdate:
description: update falloutcraft status effect. usage: "/foupdate"

fosetf:
description: set the player's fatigue value. usage: "/fosetf playerid value"

fosetd:
description: set the player's dehydration value. usage: "/fosetd playerid value"

fosetr:
description: set the player's radiation value. usage: "/fosetr playerid value"



====Language Setting====
1 . The messages are all configurable. You can edit them based on the name tag of config. I made it easy to understand.
2 . messages.yml : You can edit the message.yml for customized language and messages. Notice that if you use unicode character, say the color charaters code in minecraft. Those characters' format are as \u000F.
3 . If you've done editing your language version. Just replace messages.yml. You can edit them based on the tag of config. The config are quiet easy to understand. For example : YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL: "your custom text here"

====Acknowledgement for those players support me with their donations====

(In game name)：
past1980,MikeW138,alexkok926,ele225588,Volatus,stone_family,BakeWing,elvis0130,TW_qaz135101404,as983056,guanlinbao,Fc_Ka1to,Minecraftred4157,leungchinki,guanlinbao,leo1493,Bianco,nece99,solw,RivenSticky,BelleJieer,bruce8866,wl02180372,09533225,john0518,stone_family,JokeIsOnYou,WangKa,CGtimmy,TNTcat_tw,Feather__,TNTcat_tw,stone_family,dark_electric,poo861217,Jerrol_Evan,jreey651050,Rice_Cakes_Soul,xsxsxs123123,Kevin_0808,shouhuyuyi,AdenYO,handsomeonface,stone_family,Los_Roubaix,XaiSuR_,MysticSecret,stone_family,Lienax,aass1969,Kiritani8980,stone_family,a98776544z,stone_family,ping89116,WoohooWeehoo,Rice_Cakes_Soul,ElderKuo,NLHS910101,lycheeQQ,Rice_Cakes_Soul,TNTcat_tw,TNTcat_tw,a800338211
