tellraw @s {"text":"Borrando la casa...","color":"yellow"}
kill @e[type=minecraft:wolf,x=2,y=195,z=96,dx=22,dy=28,dz=25]
kill @e[type=minecraft:cat,x=2,y=195,z=96,dx=22,dy=28,dz=25]
fill 2 196 96 24 223 121 air
fill 2 195 96 24 195 121 grass_block
tellraw @s {"text":"Listo, terreno limpio.","color":"green"}
