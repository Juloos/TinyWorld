# TinyWorld
This is a proof of concept mod. 
Created to show Mojang how much better the game could be if they put a couple more days of development into scaling.  
Cause let's be honest here. The current system is pretty bad and just "cool" to have. Where it could be useful and allow for sick minigames and immersive gameplay.


---

## What it currently does:
### Scaling
- Fixes the camera clipping into blocks [MC-267376](https://bugs.mojang.com/browse/MC-267376)
- Fixes multiple animations not scaling correctly (walking, flying)
- Fixes the fov being too small, when you scale the movement speed with the scale.
- Fixes camera scales closer to walls when small [MC-268648](https://bugs.mojang.com/browse/MC-268648)
- Fixes movement direction snapping to the closest axis direction when low speed [MC-184530](https://bugs.mojang.com/browse/MC-184530)
### Collision
- Adds support for Block collision shape overriding (Using the model shape as the collision shape)
- Corrects the collision shape of some blocks:
  - Fences, Walls & Fence gates
  - Buttons & Pressure plates
  - Signs & Hanging signs
  - Candles, Sea pickles & Turtle eggs
  - End rods & Lightning rods
  - Campfires
  - Brewing stands
  - Bells
  - Dragon eggs
  - Flower pots (with and without plants)
  - Chorus plants & Chorus flowers
  - Bamboo

So why these changes?  
well, with these changes, you are able to make block models that are the size of a chunk section. 
Then you can scale the player to be 1/16 the size, and have them walk around in those blocks as if they are the full size.  
It gives the illusion that everything is normal. This can be used for adventure maps, minigames, and cool server setups.

---

## TODO:
### Bugs
- Custom block collisions aren't removed when the resource pack no longer has them...
- Dropping items while scaled down to 1/16th causes items to randomly launch into the air
- Being scaled down to 1/16th and on top of a shulker box while opening it makes you fall down inside it 
### Scaling
- View bobbing doesn't scale [MC-267368](https://bugs.mojang.com/browse/MC-267368)
- Nametags don't get scaled [MC-267599](https://bugs.mojang.com/browse/MC-267599)*
- (Sprint particles done) Fix particles created by scaled entities not being scaled. ([eating](https://bugs.mojang.com/browse/MC-270594), terrain, potions, etc[.](https://bugs.mojang.com/browse/MC-270878)[.](https://bugs.mojang.com/browse/MC-270942)[.](https://bugs.mojang.com/browse/MC-270801))
### Scaling Up
- Actions sounds not working correctly [MC-270592](https://bugs.mojang.com/browse/MC-270592)
- Sneaking no longer works [MC-268917](https://bugs.mojang.com/browse/MC-268917)*

`*`: Mojang being stupid.

### Features
- The slowdown of webs inversely scales with the player scale
- Leaves are not colliding with players that are scaled down to 1/8th or smaller
- Small dripleaves collide with players that are scaled down to 1/8th or smaller
- Big dripleaves do not fold when players that are on top of them are scale down to 1/8th or smaller
- Plants, crops and flowers slow down players that are scaled down to 1/8th or smaller
- Some non-herbivore mobs will try to eat players that are scaled down to 1/8th or smaller
- Fire sources will damage the player depending on its scale (lit candles, torches, campfires...)
- Correct the collision shape of some blocks (if possible):
  - Lecterns
  - Banners
  - Torches and Levers
  - Skulls
  - Iron bars, Doors and Trapdoors

---
## Tiny World Creation
Wait that's what this mod is about?! We only just got to it!  
This mod comes with a tool which allows you to convert chunk sections to block models. 
Which you can then use to shrink yourself and walk around in.  

### How to create tiny world
Simply use the command `/tiny <name> <from> <to>`, where `from` & `to` are section positions. 
It will convert those sections into block models are automatically make a resource pack with the changes.  
  
Currently, it just makes custom models for stained glass blocks. When using the resource pack, 
just place stained glass to have them in the world.

### Limitations
The system currently only does full blocks. Since this is a proof of concept, 
and im pretty busy, so im not sure if ill have time to automatically get block models and shrink them.  
You also shouldn't use any translucent or transparent blocks in the sections, 
this is because block models don't do translucency sorting on themselves.

### Attributes
Here are the commands that will shrink you by 16x. It should account for everything correctly.
```
/execute as @a run attribute @s minecraft:generic.scale base set 0.0625
/execute as @a run attribute @s minecraft:generic.step_height base set 0.0375
/execute as @a run attribute @s minecraft:generic.jump_strength base set 0.02625
/execute as @a run attribute @s minecraft:generic.gravity base set 0.005
/execute as @a run attribute @s minecraft:generic.safe_fall_distance base set 0.1875
/execute as @a run attribute @s minecraft:generic.fall_damage_multiplier base set 16
/execute as @a run attribute @s minecraft:generic.movement_speed base set 0.00625
/execute as @a run attribute @s minecraft:generic.knockback_resistance base set 0.9375
/execute as @a run attribute @s minecraft:player.block_interaction_range base set 0.28125
/execute as @a run attribute @s minecraft:player.entity_interaction_range base set 0.1875
```

---

<img src="https://github.com/fxmorin/TinyWorld/blob/master/img/example.png?raw=true" alt="Orb example"/>
