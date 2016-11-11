package org.softshack.trackme.tests.mapsactivityview;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.DataSetMapper;
import org.softshack.trackme.MapsActivityModel;
import org.softshack.trackme.MapsActivityView;
import org.softshack.trackme.TrackLocation;
import org.softshack.trackme.ViewComponents;
import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IDialog;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.util.HashMap;
import java.util.UUID;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMethodCalls {

    MapsActivityView mapsActivityView;

    @Mock
    ViewComponents mockViewComponents;

    @Mock
    MapsActivityModel mockMapsActivityModel;

    @Mock
    ITrackMap mockTrackMap;

    @Mock
    TrackLocation mockTrackLocation;

    @Mock
    IButton mockYearButton;

    @Mock
    IDialog mockYearPicker;

    @Mock
    DefaultEvent<EventArgs> mockEvent;

    @Mock
    HashMap<String, DataSetMapper> mockPositions;

    @Before
    public void setup() throws Exception {
        when(mockViewComponents.getMapsActivityModel()).thenReturn(mockMapsActivityModel);
        when(mockViewComponents.getTrackMap()).thenReturn(mockTrackMap);
        when(mockViewComponents.getYearButton()).thenReturn(mockYearButton);
        when(mockViewComponents.getYearPicker()).thenReturn(mockYearPicker);

        when(mockTrackMap.getOnMapIdle()).thenReturn(mockEvent);
        when(mockYearButton.getOnClicked()).thenReturn(mockEvent);
        when(mockYearPicker.getOnYearChanged()).thenReturn(mockEvent);
    }

    @Test
    public void testInitialize() throws Exception {
        // Arrange
        this.mapsActivityView = new MapsActivityView(mockViewComponents);

        // Act
        this.mapsActivityView.initialize();

        // Assert
        verify(this.mockMapsActivityModel, times(1)).getAllowUserToCentreMap();
        verify(this.mockTrackMap, times(1)).allowUserToCentreMap(anyBoolean());
    }

    @Test
    public void testSetMapPositionCurrent() throws Exception{
        //Arrange
        double fakeLatitudeValue = 1.0;
        double fakeLongitudeValue = fakeLatitudeValue * 2;

        this.mapsActivityView = new MapsActivityView(mockViewComponents);

        when(this.mockMapsActivityModel.getCurrentLatitude()).thenReturn(fakeLatitudeValue);
        when(this.mockMapsActivityModel.getCurrentLongitude()).thenReturn(2.0);

        // Act
        this.mapsActivityView.setMapPositionCurrent();

        // Assert
        verify(this.mockMapsActivityModel, times(1)).getCurrentLatitude();
        verify(this.mockMapsActivityModel, times(1)).getCurrentLongitude();
        verify(this.mockTrackMap, times(1)).setMapPosition(fakeLatitudeValue, fakeLongitudeValue);
    }

    @Test
    public void testGetMapCentre() throws Exception{
        // Arrange
        double fakeLatitudeValue = 1.0;
        double fakeLongitudeValue = fakeLatitudeValue * 2;

        this.mapsActivityView = new MapsActivityView(mockViewComponents);

        when(this.mockTrackMap.getMapCentre()).thenReturn(mockTrackLocation);
        when(this.mockTrackLocation.getLatitude()).thenReturn(fakeLatitudeValue);
        when(this.mockTrackLocation.getLongitude()).thenReturn(fakeLongitudeValue);

        // Act
        this.mapsActivityView.getMapCentre();

        // Assert
        verify(this.mockTrackMap, times(1)).getMapCentre();
        verify(this.mockTrackLocation, times(1)).getLatitude();
        verify(this.mockTrackLocation, times(1)).getLongitude();
        verify(this.mockMapsActivityModel, times(1)).setCurrentLatitude(fakeLatitudeValue);
        verify(this.mockMapsActivityModel, times(1)).setCurrentLongitude(fakeLongitudeValue);
    }

    @Test
    public void testClearMap() throws Exception{
        // Arrange
        this.mapsActivityView = new MapsActivityView(mockViewComponents);

        // Act
        this.mapsActivityView.clearMap();

        // Assert
        verify(this.mockTrackMap, times(1)).clearMap();
    }

    @Test
    public void testBuildHeatMap() throws Exception{
        // Arrange
        String fakeKey = UUID.randomUUID().toString();

        this.mapsActivityView = new MapsActivityView(mockViewComponents);

        when(this.mockMapsActivityModel.getPositions()).thenReturn(mockPositions);
        when(this.mockMapsActivityModel.getPositionsKey()).thenReturn(fakeKey);

        // Act
        this.mapsActivityView.buildHeatMap();

        // Assert
        verify(this.mockTrackMap, times(1)).buildHeatMap(this.mockPositions, fakeKey);
    }

    @Test
    public void testUpdateYear() throws Exception {
        // Arrange
        this.mapsActivityView = new MapsActivityView(mockViewComponents);

        // Act
        this.mapsActivityView.updateYear();

        // Assert
        verify(this.mockYearPicker, times(1)).show();
    }
}