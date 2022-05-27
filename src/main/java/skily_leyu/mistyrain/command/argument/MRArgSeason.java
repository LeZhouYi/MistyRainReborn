package skily_leyu.mistyrain.command.argument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;
import skily_leyu.mistyrain.common.core.time.MRSeason;

public class MRArgSeason implements ArgumentType<MRSeason>{

    private static final SimpleCommandExceptionType ERROR_INVALID_SEASON = new SimpleCommandExceptionType(new TranslationTextComponent("argument.time.invalid_season"));

    public static Map<String,Integer> examples = new HashMap<>();
    static{
        examples.put("spring", 0);
        examples.put("summer", 1);
        examples.put("autumn", 2);
        examples.put("winter", 3);
    }

    public static MRArgSeason getArgSeason(){
        return new MRArgSeason();
    }

    @Override
    public MRSeason parse(StringReader reader) throws CommandSyntaxException {
        String seasonStr = reader.readString();
        if(examples.containsKey(seasonStr)){
            return MRSeason.values()[examples.get(seasonStr)];
        }
        throw ERROR_INVALID_SEASON.create();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader stringreader = new StringReader(builder.getRemaining());

        try {
            stringreader.readFloat();
        } catch (CommandSyntaxException commandsyntaxexception) {
            return builder.buildFuture();
        }

        return ISuggestionProvider.suggest(examples.keySet(), builder.createOffset(builder.getStart() + stringreader.getCursor()));
    }

    @Override
    public Collection<String> getExamples() {
        return examples.keySet();
    }

}
