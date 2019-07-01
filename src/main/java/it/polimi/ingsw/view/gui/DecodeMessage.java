package it.polimi.ingsw.view.gui;

import com.sun.javafx.fxml.builder.JavaFXImageBuilder;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.weapons.Zx2;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.cli.graph.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.tools.JavaFileObject;
import java.io.File;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DecodeMessage {

    private Map<Character, Path> mapCharacterPlayerboard = new EnumMap<Character, Path>(Character.class);
    private Map<String,Path> mapWeapon = new HashMap<String,Path>();
    private Map<String,Path> mapPowerUpColor = new HashMap<String,Path>();


    /**
     * Constructor: set relation between character and playerboard image,
     *              character andhis marker,
     *              weapon and card,
     *              powerUp and card,
     *              ammoTile and card.
     */
    public DecodeMessage() {
        Path dstructor = getPath("playerBoard/yellowPlayerBoard.png");
        Path banshee = getPath("playerBoard/bluePlayerBoard.png");
        Path violet = getPath("playerBoard/violetPlayerBoard.png");
        Path dozer = getPath("playerBoard/greyPlayerBoard.png");
        Path sprog = getPath("playerBoard/greenPlayerBoard.png");
        //setting character  e relativa playerboard
        mapCharacterPlayerboard.put(Character.D_STRUCT_OR, dstructor);
        mapCharacterPlayerboard.put(Character.BANSHEE, banshee);
        mapCharacterPlayerboard.put(Character.DOZER, dozer);
        mapCharacterPlayerboard.put(Character.VIOLET,violet);
        mapCharacterPlayerboard.put(Character.SPROG, sprog);
        Path cyberBlade = getPath("cards/weapon/cyberBlade.png");
        Path electroscythe = getPath("cards/weapon/electroscythe.png");
        Path flamethrower = getPath("cards/weapon/flamethower.png");
        Path furnace = getPath("cards/weapon/furnace.png");
        Path grenadaLauncher = getPath("cards/weapon/grenadaLauncher.png");
        Path heatseeker = getPath("cards/weapon/heatseeker.png");
        Path hellion = getPath("cards/weapon/hellion.png");
        Path lockRifle = getPath("cards/weapon/lockRifle.png");
        Path machineGun = getPath("cards/weapon/machineGun.png");
        Path plasmaGun = getPath("cards/weapon/plasmaGun.png");
        Path powerGlove= getPath("cards/weapon/powerGlove.png");
        Path railgun = getPath("cards/weapon/railgun.png");
        Path rocketLauncher= getPath("cards/weapon/rocketLauncher.png");
        Path shockwave = getPath("cards/weapon/shockwave.png");
        Path shotgun= getPath("cards/weapon/shotgun.png");
        Path sledgeHammer= getPath("cards/weapon/sledgeHammer.png");
        Path thor= getPath("cards/weapon/thor.png");
        Path tractorBeam= getPath("cards/weapon/tractorBeam.png");
        Path vortexCannon= getPath("cards/weapon/vortexCannon.png");
        Path whisper= getPath("cards/weapon/whisper.png");
        Path zx2= getPath("cards/weapon/zx2.png");
        Path unload= getPath("cards/weapon/unload.png");
        //setting weapon e relativa carta
        mapWeapon.put( "CYBER BLADE",cyberBlade);
        mapWeapon.put( "ELECTROSCYTHE",electroscythe);
        mapWeapon.put( "FLAMETHROWER",flamethrower);
        mapWeapon.put( "FURNACE",furnace);
        mapWeapon.put( "GRENADE LAUNCHER",grenadaLauncher);
        mapWeapon.put( "HEATSEEKER",heatseeker);
        mapWeapon.put( "HELLION",hellion);
        mapWeapon.put( "LOCK RIFLE",lockRifle);
        mapWeapon.put( "MACHINE GUN",machineGun);
        mapWeapon.put( "PLASMA GUN",plasmaGun);
        mapWeapon.put( "POWER GLOVE",powerGlove);
        mapWeapon.put( "RAILGUN",railgun);
        mapWeapon.put( "ROCKET LAUNCHER",rocketLauncher);
        mapWeapon.put( "SHOCKWAVE",shockwave);
        mapWeapon.put( "SHOTGUN",shotgun);
        mapWeapon.put( "SLEDGEHAMMER",sledgeHammer);
        mapWeapon.put( "T.H.O.R.",thor);
        mapWeapon.put( "TRACTOR BEAM",tractorBeam);
        mapWeapon.put( "VORTEX CANNON",vortexCannon);
        mapWeapon.put( "WHISPER",whisper);
        mapWeapon.put( "ZX-2", zx2);
        //setting PowerUp e relativa card
        Path newtonRed = getPath("cards/powerUp/newtonRed.png");
        Path newtonYellow = getPath("cards/powerUp/newtonYellow.png");
        Path newtonBlue = getPath("cards/powerUp/newtonBlue.png");
        Path scopeRed = getPath("cards/powerUp/scopeRed.png");
        Path scopeYellow = getPath("cards/powerUp/scopeYellow.png");
        Path scopeBlue = getPath("cards/powerUp/scopeBlue.png");
        Path grenadaRed = getPath("cards/powerUp/granataRed.png");
        Path grenadaYellow = getPath("cards/powerUp/granataYellow.png");
        Path grenadaBlue = getPath("cards/powerUp/granataBlue.png");
        Path teleporterRed = getPath("cards/powerUp/teleporterRed.png");
        Path teleporterYellow = getPath("cards/powerUp/teleporterYellow.png");
        Path teleporterBlue = getPath("cards/powerUp/teleporterBlue.png");
        mapPowerUpColor.put("Newton"+"Red",newtonRed);
        mapPowerUpColor.put("Newton"+"Yellow",newtonYellow);
        mapPowerUpColor.put("Newton"+"Blue",newtonBlue);
        mapPowerUpColor.put("TagbackGrenade"+"Red",grenadaRed);
        mapPowerUpColor.put("TagbackGrenade"+"Yellow",grenadaYellow);
        mapPowerUpColor.put("TagbackGrenade"+"Blue",grenadaBlue);
        mapPowerUpColor.put("TargetingScope"+"Red",scopeRed);
        mapPowerUpColor.put("TargetingScope"+"Yellow",scopeYellow);
        mapPowerUpColor.put("TargetingScope"+"Blue",scopeBlue);
        mapPowerUpColor.put("Teleporter"+"Red",teleporterRed);
        mapPowerUpColor.put("Teleporter"+"Yellow",teleporterYellow);
        mapPowerUpColor.put("Teleporter"+"Blue",teleporterBlue);

    }

    /**
     * It finds path by the string passed
     * @param convert string which is the path
     * @return path of the file
     */
    private Path getPath(String convert){
    Path path = null;
    File resources = new File(convert);
    try {
        path = resources.toPath();
    }catch (NullPointerException e){
        System.out.println("errore acceso file");
    }
    return path;
    }

    /**
     * It finds ammotile image by its component
     * @param firstcolor first ammo color
     * @param secondColor second ammo color
     * @param thrirdColor thrid ammo color or one powerUp
     * @return path of the image
     */
    public Path findAmmoTileImage(String firstcolor, String secondColor, String thrirdColor){
        char firstChar = firstcolor.toLowerCase().charAt(0);
        char secondChar = secondColor.toLowerCase().charAt(0);
        char thirdChar = thrirdColor.toLowerCase().charAt(0);
        return getPath("cards/ammoTile/"+firstChar+secondChar+thirdChar+".png");
    }

    /**
     *i finds weapon image by weapon name
     * @param weapon weapon name
     * @return path of the image
     */
    public Path findWeaponImage(String weapon){
        return mapWeapon.get(weapon);
    }

    /**
     * it finds powerUp image by its name and color
     * @param powerUpName name of the powerUp
     * @param powerUpColor color of the powerUp
     * @return path of the image
     */
    public Path findPowerUpImage(String powerUpName, CubeColour powerUpColor){
        return mapPowerUpColor.get(powerUpName+powerUpColor);
    }

    /**
     * It finds playerboard image by the character
     * @param character character of the powerUP
     * @return path of the image

     */
    public Path findPlayerBoardImage(Character character){
        return mapCharacterPlayerboard.get(character);
    }

    /**
     * It finds map images by number selected
     * @param mapNumber number of the map
     * @return paths of the two map images
     */
    public Path[] findMapImages(int mapNumber){
        Path[] pathMap = new Path[2];

            switch(mapNumber) {
                case 0:
                    pathMap[0] = getPath("map/left1map.png");
                    pathMap[1] = getPath("map/right1map.png");
                    break;
                case 1:
                    pathMap[0] = getPath("map/left1map.png");
                    pathMap[1] = getPath("map/right2map.png");
                    break;
                case 2:
                    pathMap[0] = getPath("map/left2map.png");
                    pathMap[1] = getPath("map/right1map.png");
                    break;
                case 3:
                    pathMap[0] = getPath("map/left2map.png");
                    pathMap[1] = getPath("map/right2map.png");
                    break;

        }
        return pathMap;
    }

    /**
     * It gets image from path
     * @param path location of ine image
     * @return image of path
     */
    public Image loadImage(Path path){
        Image image = new Image(path.toString());
        return image;
    }

    /**
     *
     * @param character
     * @return
     */
   public Image playerBoardImage(Character character){
       Path path =  findPlayerBoardImage(character);
       return loadImage(path);
    }

    /**
     *
     * @param weapon
     * @return
     */
    public Image weaponImage(String weapon){
        Path path =  findWeaponImage(weapon);
        return loadImage(path);
    }

    /**
     *
     * @param name
     * @param colour
     * @return
     */
    public Image powerUpImage(String name, CubeColour colour){
        Path path =  findPowerUpImage(name,colour);
        return loadImage(path);
    }

    /**
     *
     * @param number
     * @return
     */
    public Image[] mapImage(int number){
        Path pathLeft = findMapImages(number)[0];
        Path pathRight = findMapImages(number)[1];
        Image[] map = new Image[2];
        map[0] = loadImage(pathLeft);
        map[1] = loadImage(pathRight);
        return map;
    }

    /**
     *
     * @param firstColor
     * @param secondColor
     * @param thrirdColor
     * @return
     */
    public Image ammoimage(String firstColor,String secondColor, String thrirdColor){
        Path path =  findAmmoTileImage(firstColor,secondColor,thrirdColor);
        return loadImage(path);
    }
}