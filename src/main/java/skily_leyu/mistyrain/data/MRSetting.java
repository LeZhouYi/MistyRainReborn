package skily_leyu.mistyrain.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.book.Book;
import skily_leyu.mistyrain.common.core.plant.PlantMap;
import skily_leyu.mistyrain.common.core.pot.PotMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class MRSetting {

    private static PlantMap potPlants;
    private static PotMap potMap;
    private static Book herbalsBook;

    private MRSetting() {
    }

    public static void load(){
        potPlants = loadJson(PlantMap.class, "mr_pot_plants");
        potMap = loadJson(PotMap.class, "mr_pots");
        herbalsBook = loadJson(Book.class,"mr_herbals_book");
    }

    private static <T> T loadJson(Class<T> jsonClass, String registryName) {
        return loadJson(jsonClass, registryName, MistyRain.MOD_ID);
    }

    public static <T> T loadJson(Class<T> jsonClass, String registryName, String modID) {
        String filePath = String.format("/assets/%s/setting/%s.json", modID, registryName);
        T result = null;
        InputStream inputStream = MRSetting.class.getResourceAsStream(filePath);
        if (inputStream != null) {
            try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                Gson gson = new GsonBuilder().create();
                result = gson.fromJson(reader, jsonClass);
            } catch (IOException e) {
                MistyRain.getLogger().log(Level.ERROR,e.getStackTrace());
            }
        }

        return result;
    }

    public static PlantMap getPlantMap() {
        return potPlants;
    }

    public static PotMap getPotMap() {
        return potMap;
    }

    public static Book getHerbalsBook(){return herbalsBook;}

}
