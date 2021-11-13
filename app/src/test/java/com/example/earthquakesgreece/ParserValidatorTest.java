package com.example.earthquakesgreece;

import static com.google.common.truth.Truth.assertThat;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ParserValidatorTest {

    @Test
    public void parserDescription() throws IOException, XmlPullParserException {

//        final InputStream inputStream = resources.openRawResource(R.raw.quakeml);
//        QuakemlParser qml = new QuakemlParser();
//        qml.parse(inputStream);

        Resources resources = ApplicationProvider.getApplicationContext().getResources();
        XmlResourceParser parser = resources.getXml(R.raw.quakeml);

        assertThat(parser).isNotNull();

        assertThat(parser.getAttributeCount()).isEqualTo(-1);

        assertThat(parser.next()).isEqualTo(XmlPullParser.START_DOCUMENT);
        assertThat(parser.next()).isEqualTo(XmlPullParser.START_TAG);

        assertThat(parser.getName()).isEqualTo("whatever");
    }
}
