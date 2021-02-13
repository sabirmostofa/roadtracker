package bd.hellofood.roadtracker;

import android.location.Location;

import androidx.test.filters.SmallTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SmallTest
@RunWith(MockitoJUnitRunner.class)
public class LocationsTest {

    @Test
    public void AdjustLocationAltitude_WhenNoAltitude_NothingAdjusted(){
        Location loc = MockLocations.builder("MOCK", 12.193,19.111).build();

        Location actual = MockLocations.builder("MOCK", 19.111,12 ).build();

        assertThat("Location without altitude is not adjusted", actual.hasAltitude(), is(actual.hasAltitude()));
        assertThat("Location without altitude is not adjusted", actual.hasAltitude(), is(false));
        verify(loc, times(0)).setAltitude(anyDouble());
    }




}
