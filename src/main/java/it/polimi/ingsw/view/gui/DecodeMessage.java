package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import javafx.scene.image.Image;
import java.io.File;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * It links an image and a card, map, character, playerboard
 */
public class DecodeMessage {

    private Map<Character, Path> mapCharacterPlayerboard = new EnumMap<Character, Path>(Character.class);
    private Map<String,Path> mapWeapon = new HashMap<String,Path>();
    private Map<String,Path> mapPowerUpColor = new HashMap<String,Path>();


    /**
     * Constructor: set relation between character and playerboard image,
     *              character and his marker,
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
        mapWeapon.put("unload",unload);
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
     * It finds ammo tile path by its component
     * @param firstColor first ammo color
     * @param secondColor second ammo color
     * @param thirdColor thrid ammo color or one powerUp
     * @return path of the image
     */
    private Path findAmmoTileImage(String firstColor, String secondColor, String thirdColor){
        char firstChar = firstColor.toLowerCase().charAt(0);
        char secondChar = secondColor.toLowerCase().charAt(0);
        char thirdChar = thirdColor.toLowerCase().charAt(0);
        return getPath("cards/ammoTile/"+firstChar+secondChar+thirdChar+".png");
    }

    /**
     *it finds weapon path by weapon name
     * @param weapon weapon name
     * @return path of the image
     */
    private Path findWeaponImage(String weapon){
        return mapWeapon.get(weapon);
    }

    /**
     * it finds powerUp path by its name and color
     * @param powerUpName name of the powerUp
     * @param powerUpColor color of the powerUp
     * @return path of the image
     */
    private Path findPowerUpImage(String powerUpName, CubeColour powerUpColor){
        return mapPowerUpColor.get(powerUpName+powerUpColor);
    }

    /**
     * It finds ammo cube path by ammo cube color
     * @param ammo ammo cube to find
     * @return path of the image
     */
    private Path findAmmoCubeImage(AmmoCube ammo){
        Path path = null;
        if (ammo.getColour() == CubeColour.Red){
            path = getPath("ammoboxes/redammobox.png");
        }else if (ammo.getColour() == CubeColour.Yellow){
            path = getPath("ammoboxes/yellowammobox.png");
        }else {
            path = getPath("ammoboxes/blueammobox.png");
        }

        return path;
    }
    /**
     * It finds player board path by the character
     * @param character character of the powerUP
     * @return path of the image
     */
    private Path findPlayerBoardImage(Character character){
        return mapCharacterPlayerboard.get(character);
    }

    /**
     * It finds map paths by number selected
     * @param mapNumber number of the map
     * @return paths of the two map images
     */
    private Path[] findMapImages(int mapNumber){
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
    private Image loadImage(Path path){
        return new Image(path.toString());
    }

    /**
     * It gets image of a playerboard from a character
     * @param character playerboard to find
     * @return image of a playerboard
     */
   public Image playerBoardImage(Character character){
       Path path =  findPlayerBoardImage(character);
       return loadImage(path);
    }

    /**
     * It gets image of a weapon from a weapon name
     * @param weapon weapon name
     * @return weapon image
     */
    public Image weaponImage(String weapon){
        Path path =  findWeaponImage(weapon);
        return loadImage(path);
    }

    /**
     * It gets image of a powerUp from powerUP name and color
     * @param name name of a powerUp
     * @param colour color of a powerUp
     * @return powerUp image
     */
    public Image powerUpImage(String name, CubeColour colour){
        Path path =  findPowerUpImage(name,colour);
        return loadImage(path);
    }

    /**
     * It gets images of a map from a number
     * @param number nember of a map
     * @return both images(semimap) of a map
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
     * It gets image of an ammo tile from its component
     * @param firstColor ammo cube
     * @param secondColor ammo cube
     * @param thrirdColor ammo cube or powerUp
     * @return image of an ammo Tile
     */
    public Image ammoTileImage(String firstColor, String secondColor, String thrirdColor){
        Path path =  findAmmoTileImage(firstColor,secondColor,thrirdColor);
        return loadImage(path);
    }

    /**
     * It gets image of an ammo cube
     * @param ammoCube ammo cube to find
     * @return image of the ammo cube
     */
    public Image ammoCubeImage(AmmoCube ammoCube){
        Path  path = findAmmoCubeImage(ammoCube);
        return loadImage(path);
    }

    /**
     * it finds player token path by character
     * @param character character of token to find
     * @return character token path
     */
    private Path findPlayerTokenImage(Character character){
        Path path = null;
        switch(character) {
            case VIOLET:
                path = getPath("playerTokens/violetToken.png");
                break;
            case SPROG:
                path = getPath("playerTokens/sprogToken.png");
                break;
            case BANSHEE :
                path = getPath("playerTokens/bansheeToken.png");
                break;
            case DOZER:
                path = getPath("playerTokens/dozerToken.png");
                break;
            case D_STRUCT_OR:
                path = getPath("playerTokens/dstructorToken.png");
                break;

        }
                return path;
    }

    /**
     * it finds character path by character
     * @param character character to find
     * @return path of the image
     */
    private Path findCharacterImage(Character character) {
        Path path = null;
        switch (character) {
            case VIOLET:
                path = getPath("players/violet.png");
                break;
            case SPROG:
                path = getPath("players/sprog.png");
                break;
            case BANSHEE:
                path = getPath("players/banshee.png");
                break;
            case DOZER:
                path = getPath("players/dozer.png");
                break;
            case D_STRUCT_OR:
                path = getPath("players/dstructor.png");
                break;
        }
        return path;
    }

    /**
     *it gets image of character token
     * @param character character to find
     * @return image of character token
     */
    public Image playerTokenImage(Character character) {
        Path path = findPlayerTokenImage(character);
        return loadImage(path);

    }


    /**
     *it gets image of character
     * @param character character to find
     * @return image of character
     *
     */
    public Image characterImage(Character character){
        Path path= findCharacterImage(character);
        return loadImage(path);
    }
}

