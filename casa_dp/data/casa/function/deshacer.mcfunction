tellraw @s {"text":"Borrando la casa de jungla...","color":"yellow"}
kill @e[type=minecraft:item_frame,x=4,y=195,z=115,dx=23,dy=30,dz=22]
fill 4 196 115 27 225 137 air
fill 4 195 115 27 195 137 grass_block
tellraw @s {"text":"Listo, terreno limpio.","color":"green"}
