package edu.cmu.sv.mymapboxdemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mapbox.mapboxsdk.api.ILatLng;
import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.GpsLocationProvider;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.tileprovider.tilesource.ITileLayer;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MapboxTileLayer;
import com.mapbox.mapboxsdk.views.MapController;
import com.mapbox.mapboxsdk.views.MapView;
import com.mapbox.mapboxsdk.views.util.TilesLoadedListener;

public class MainActivity extends Activity {
	
	private static final String MSG_TAG = "MainActivity";

    private MapView mv;
    private String satellite = "brunosan.map-cyglrrfu";
    private String street = "examples.map-i87786ca";
    private String terrain = "examples.map-zgrqqx0w";
    private String currentLayer = "satellite";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mv = (MapView) findViewById(R.id.mapview);

		MapController mapController = (MapController) mv.getController();
        replaceMapView(satellite);
        addLocationOverlay();

        mv.loadFromGeoJSONURL("https://gist.githubusercontent.com/tmcw/10307131/raw/21c0a20312a2833afeee3b46028c3ed0e9756d4c/map.geojson");
        setButtonListeners();
        Marker m = new Marker(mv, "Ching's home", "Taipei, Taiwan", new LatLng(25.0333, 121.6333));
        m.setIcon(new Icon(this, Icon.Size.SMALL, "marker-stroked", "FF0000"));
        mv.addMarker(m);

        m = new Marker(mv, "Isil's home", "Istanbul, Turkey", new LatLng(41.0136, 28.9550));
        m.setIcon(new Icon(this, Icon.Size.SMALL, "city", "FFFF00"));
        mv.addMarker(m);

        m = new Marker(mv, "David's home", "Kaohsiung", new LatLng(22.6333, 120.2667));
        m.setIcon(new Icon(this, Icon.Size.SMALL, "land-use", "00FFFF"));
        mv.addMarker(m);

        m = new Marker(mv, "Mia's home", "Manila, Philippines", new LatLng(14.5833, 120.9667));
        m.setIcon(new Icon(getBaseContext(), Icon.Size.SMALL, "land-use", "00FF00"));
        mv.addMarker(m);

        m = new Marker(mv, "Emi's home", "Buenos Aires, Argentina", new LatLng(-34.6033, -58.3817));
        m.setIcon(new Icon(getBaseContext(), Icon.Size.SMALL, "land-use", "00FF00"));
        mv.addMarker(m);

        mv.setOnTilesLoadedListener(new TilesLoadedListener() {
            @Override
            public boolean onTilesLoaded() {
                return false;
            }

            @Override
            public boolean onTilesLoadStarted() {
                // TODO Auto-generated method stub
                return false;
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    protected void replaceMapView(String layer) {
        ITileLayer source;
        BoundingBox box;

        source = new MapboxTileLayer(layer);

        mv.setTileSource(source);
        box = source.getBoundingBox();
        mv.setScrollableAreaLimit(box);
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(mv.getTileProvider().getCenterCoordinate());
        mv.setZoom(0);
    }

    private void addLocationOverlay() {
//    	TODO
//    	NullPointer error at line 129 
        // Adds an icon that shows location
//		UserLocationOverlay myLocationOverlay = new UserLocationOverlay(new GpsLocationProvider(this), mv);
//		
//        myLocationOverlay.enableMyLocation();
//        myLocationOverlay.setDrawAccuracyEnabled(true);
//        mv.getOverlays().add(myLocationOverlay);
    }

    private Button changeButtonTypeface(Button button) {
        return button;
    }

    public LatLng getMapCenter() {
        return mv.getCenter();
    }

    public void setMapCenter(ILatLng center) {
        mv.setCenter(center);
    }
    
    private void setButtonListeners() {
        Button satBut = changeButtonTypeface((Button) findViewById(R.id.satbut));
        satBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentLayer.equals("satellite")) {
                    replaceMapView(satellite);
                    currentLayer = "satellite";
                }
            }
        });
        Button terBut = changeButtonTypeface((Button) findViewById(R.id.terbut));
        terBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentLayer.equals("terrain")) {
                    replaceMapView(terrain);
                    currentLayer = "terrain";
                }
            }
        });
        Button strBut = changeButtonTypeface((Button) findViewById(R.id.strbut));
        strBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentLayer.equals("street")) {
                    replaceMapView(street);
                    currentLayer = "street";
                }
            }
        });
        Button bugsBut = changeButtonTypeface((Button) findViewById(R.id.bugsButton));
        bugsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/mapbox/mapbox-android-sdk/issues?state=open";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
