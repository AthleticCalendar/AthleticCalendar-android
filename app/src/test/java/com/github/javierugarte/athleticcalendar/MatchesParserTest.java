package com.github.javierugarte.athleticcalendar;

import com.github.javierugarte.athleticcalendar.network.MatchesParser;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MatchesParserTest {

    @Test
    public void return_null_when_empty_json () throws Exception {
        MatchesParser parser = new MatchesParser();
        assertNull(parser.parse(null));
    }

    @Test
    public void return_null_when_json_is_invalid () throws Exception {
        String json = "{\"invalidJson\" : \"blabla}";
        MatchesParser parser = new MatchesParser();
        List<Match> matches = parser.parse(json);
        assertNull(matches);
    }

    @Test
    public void return_not_null_when_json_is_valid () throws Exception {
        String json = ResourceHelper.getStringFromFile(getClass().getResource("/response.json"));
        MatchesParser parser = new MatchesParser();
        List<Match> matches = parser.parse(json);
        assertNotNull(matches);
    }

    @Test
    public void return_size_5_when_json_is_valid () throws Exception {
        String json = ResourceHelper.getStringFromFile(getClass().getResource("/response.json"));
        MatchesParser parser = new MatchesParser();
        List<Match> matches = parser.parse(json);
        assertEquals(5, matches.size());
    }

    @Test
    public void return_title_when_json_is_valid () throws Exception {
        String json = ResourceHelper.getStringFromFile(getClass().getResource("/response.json"));
        MatchesParser parser = new MatchesParser();
        List<Match> matches = parser.parse(json);

        assertEquals("Athletic", matches.get(1).getTeam1());
    }

    @Test
    public void return_order_when_json_is_valid () throws Exception {
        String json = ResourceHelper.getStringFromFile(getClass().getResource("/response.json"));
        MatchesParser parser = new MatchesParser();
        List<Match> matches = parser.parse(json);

        assertEquals("APOEL", matches.get(0).getTeam1());
    }

    @Test
    public void return_image1_when_json_is_valid () throws Exception {
        String json = ResourceHelper.getStringFromFile(getClass().getResource("/response.json"));
        MatchesParser parser = new MatchesParser();
        List<Match> matches = parser.parse(json);

        assertEquals("http://thumb.resfu.com/img_data/escudos/medium/10076.jpg?size=60x&ext=png&lossy=1&1",
                matches.get(0).getTeam1Shield());
    }

    @Test
    public void return_tvs_when_json_is_valid () throws Exception {
        String json = ResourceHelper.getStringFromFile(getClass().getResource("/response.json"));
        MatchesParser parser = new MatchesParser();
        List<Match> matches = parser.parse(json);

        assertEquals("beIN LaLiga", matches.get(1).getTvs());
    }

}