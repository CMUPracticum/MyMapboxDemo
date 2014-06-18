package edu.cmu.sv.mymapboxdemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
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
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MBTilesLayer;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MapboxTileLayer;
import com.mapbox.mapboxsdk.tileprovider.tilesource.TileLayer;
import com.mapbox.mapboxsdk.views.MapView;
import com.mapbox.mapboxsdk.views.util.TilesLoadedListener;

public class MainActivity extends Activity {

	public static final String MSG_TAG = "MainActivity";

//	Maps
    private MapView mv;
    private final String MY_MAP = "Test01.mbtiles";
    private final String TERRAIN = "examples.map-zgrqqx0w";
    private String currentLayer = MY_MAP;
    
//  Assets
	private static String ASSETS_PATH = Environment.getExternalStorageDirectory().getPath() + "/trailscribe/";
    private static String[] assets = {"maptiles/Test01.mbtiles"};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mv = (MapView) findViewById(R.id.mapview);
        
//        TODO Find a faster way to copy the tiles
//        copyAssets();

//		MapController mapController = (MapController) mv.getController();
		
        replaceMapView(MY_MAP);
        addLocationOverlay();

        mv.loadFromGeoJSONURL("https://gist.githubusercontent.com/tmcw/10307131/raw/21c0a20312a2833afeee3b46028c3ed0e9756d4c/map.geojson");
        setButtonListeners();

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
    	BoundingBox box;
    	
    	TileLayer source = null;
    	if (layer.equals(MY_MAP)) {
    		source = new MBTilesLayer(ASSETS_PATH + "Test01.mbtiles");
    	} else {
    		source = new MapboxTileLayer(layer);
    	}
    	
    	mv.setTileSource(source);
    	box = source.getBoundingBox();
    	mv.setScrollableAreaLimit(box);
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(mv.getTileProvider().getCenterCoordinate());
        mv.setZoom(mv.getTileProvider().getMinimumZoomLevel());
    }

    private void addLocationOverlay() {
    	Marker m;
    	
    	m = new Marker(mv, "CMUSV", "Buliding 23", new LatLng(37.4104, -122.0598));
    	m.setIcon(new Icon(this, Icon.Size.SMALL, "marker-stroked", "FF0000"));
    	mv.addMarker(m);
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
        Button mymapBut = changeButtonTypeface((Button) findViewById(R.id.mymapbut));
        mymapBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentLayer.equals("mymap")) {
                    replaceMapView(MY_MAP);
                    currentLayer = "mymap";
                }
            }
        });
        Button terBut = changeButtonTypeface((Button) findViewById(R.id.terbut));
        terBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentLayer.equals("terrain")) {
                    replaceMapView(TERRAIN);
                    currentLayer = "terrain";
                }
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

	private void copyAssets() {
		File directory = new File(ASSETS_PATH);
		if (!directory.exists()) {
			directory.mkdir();
		}
		
		AssetManager assetManager = this.getAssets();

		try {
			assets = assetManager.list("");
		} catch (IOException e) {
			Log.e(MSG_TAG, "Failed to get asset file list.", e);
		}

		for (String filename : assets) {
			InputStream in = null;
			OutputStream out = null;
			
			try {
				in = assetManager.open(filename);
				String newFileName = ASSETS_PATH + filename;
				out = new FileOutputStream(newFileName);
				
				byte[] buffer = new byte[1024];
				int read;
				
				while ((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}
				
				in.close();
				out.flush();
				out.close();
				
				in = null;
				out = null;
				
				File newFile = new File(newFileName);
				if (newFile.exists()) {
					Log.d(MSG_TAG, filename + "is copied to " + newFile.getAbsolutePath());
				} else {
					Log.d(MSG_TAG, "Failed to copy" + filename);
				}
			} catch (Exception e) {
				Log.e(MSG_TAG, e.getMessage());
			}
		}
	}
}
