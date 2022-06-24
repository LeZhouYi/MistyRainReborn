package skily_leyu.mistyrain.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.common.core.potplant.PotPlantList;
import skily_leyu.mistyrain.common.core.potplant.SoilMap;

public class MRSetting {

    public static SoilMap soilMap;
    public static PotPlantList potPlants;

    public MRSetting(final FMLCommonSetupEvent event){
        soilMap = loadJson(SoilMap.class, "mr_soil_map");
        potPlants = loadJson(PotPlantList.class, "mr_pot_plants");
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

}
