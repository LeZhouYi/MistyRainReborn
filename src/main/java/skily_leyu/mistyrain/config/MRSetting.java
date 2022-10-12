package skily_leyu.mistyrain.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.common.core.plant.PlantMap;
import skily_leyu.mistyrain.common.core.pot.PotMap;
import skily_leyu.mistyrain.common.core.soil.SoilMap;

public class MRSetting {

    private static SoilMap soilMap;
    private static PlantMap potPlants;
    private static PotMap potMap;

    public MRSetting(final FMLCommonSetupEvent event){
        soilMap = loadJson(SoilMap.class, "mr_soil_map");
        potPlants = loadJson(PlantMap.class, "mr_pot_plants");
        potMap = loadJson(PotMap.class, "mr_pots");
    }

    private <T> T loadJson(Class<T> jsonClass, String registryName){
        return loadJson(jsonClass, registryName,MistyRain.MOD_ID);
    }

    public static <T> T loadJson(Class<T> jsonClass, String registryName, String modID){
        String filePath = String.format("/assets/%s/setting/%s.json",modID,registryName);
        T result = null;
        try(Reader reader = new InputStreamReader(MRSetting.class.getResourceAsStream(filePath),"UTF-8")){
            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(reader, jsonClass);
        }catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static SoilMap getSoilMap(){
        return soilMap;
    }

    public static PlantMap getPlantMap(){
        return potPlants;
    }

    public static PotMap getPotMap(){
        return potMap;
    }

}
