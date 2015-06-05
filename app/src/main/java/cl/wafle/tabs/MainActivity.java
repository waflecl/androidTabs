package cl.wafle.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = MainActivity.class.getSimpleName();

    private MyTabsPagerAdapter fragmentPagerAdapter;

    private Button buttonDetails;
    private Button buttonRelated;
    private Button buttonComments;

    private ViewPager mPager;
    private PagerTabStrip pagerTab;


    private final int POSITION_DETAILS = 0;
    private final int POSITION_RELATED = 1;
    private final int POSITION_COMMENTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        pagerTab = (PagerTabStrip) findViewById(R.id.pagerTab);

        buttonDetails = (Button) findViewById(R.id.buttonDetails);
        buttonRelated = (Button) findViewById(R.id.buttonRelated);
        buttonComments = (Button) findViewById(R.id.buttonComments);

        buttonComments.setOnClickListener(this);
        buttonDetails.setOnClickListener(this);
        buttonRelated.setOnClickListener(this);


        FragmentManager fm = getSupportFragmentManager();
        HashMap<Integer, ArrayList<Object>> values = new HashMap<>();
        //First ArrayList
        ArrayList<Object> elementsRelated = new ArrayList<>();
        elementsRelated.add(new Element(1, "Primero"));
        elementsRelated.add(new Element(2, "Segundo"));
        elementsRelated.add(new Element(3, "Tercero"));
        elementsRelated.add(new Element(4, "Cuarto"));
        elementsRelated.add(new Element(5, "Quinto"));
        values.put(POSITION_RELATED, elementsRelated);
        //Second ArrayList
        ArrayList<Object> elementsDetails = new ArrayList<>();
        elementsDetails.add(new Element(1, "Primero"));
        elementsDetails.add(new Element(2, "Segundo"));
        elementsDetails.add(new Element(3, "Tercero"));
        elementsDetails.add(new Element(4, "Cuarto"));
        elementsDetails.add(new Element(5, "Quinto"));
        values.put(POSITION_DETAILS, elementsDetails);
        //Third ArrayList
        ArrayList<Object> elementsComments = new ArrayList<>();
        elementsComments.add(new Element(1, "Primero"));
        elementsComments.add(new Element(2, "Segundo"));
        elementsComments.add(new Element(3, "Tercero"));
        elementsComments.add(new Element(4, "Cuarto"));
        elementsComments.add(new Element(5, "Quinto"));
        values.put(POSITION_COMMENTS, elementsComments);

        fragmentPagerAdapter = new MyTabsPagerAdapter(fm, values);




        pagerTab.setDrawFullUnderline(true);
        pagerTab.setVisibility(View.GONE);
        pagerTab.setTabIndicatorColor(getResources().getColor(android.R.color.transparent));
        mPager.setAdapter(fragmentPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.show_tabs) {
            if(pagerTab.isShown()){
                pagerTab.setVisibility(View.GONE);
            }else {
                pagerTab.setVisibility(View.VISIBLE);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDetails:
                mPager.setCurrentItem(POSITION_DETAILS, true);
                break;
            case R.id.buttonComments:
                mPager.setCurrentItem(POSITION_COMMENTS, true);
                break;
            case R.id.buttonRelated:
                mPager.setCurrentItem(POSITION_RELATED, true);
                break;
        }
    }

    class MyTabsPagerAdapter extends PagerAdapter{
        private FragmentManager fm;
        private HashMap<Integer, ArrayList<Object>> values;
        private LayoutInflater mLayoutInflater;

        private SimpleAdapter adaterRelated = null;
        private SimpleAdapter adapterDetails = null;
        private SimpleAdapter adaptercomments = null;

        HolderDetails holderDetails = null;
        HolderRelated holderRelated = null;
        HolderComments holderComments = null;

        public MyTabsPagerAdapter(FragmentManager fm, HashMap<Integer, ArrayList<Object>> values) {
            this.fm = fm;
            this.values = values;
            mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case POSITION_DETAILS:
                    return "Detalles";
                case POSITION_RELATED:
                    return "Relacionados";
                case POSITION_COMMENTS:
                    return "Comentarios";
            }
            return "";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            switch (position){
                case POSITION_DETAILS:
                    Log.v(TAG, "POSITION_DETAILS");
                    if(holderDetails == null){
                        holderDetails = new HolderDetails();
                        holderDetails.wrap = (ViewGroup) mLayoutInflater.inflate(R.layout.viewpager_page, null);
                        holderDetails.list = (ListView) holderDetails.wrap.findViewById(R.id.list);
                    }

                    if(adapterDetails == null){
                        ArrayList<Object> objects = values.get(POSITION_DETAILS);
                        String[] items = new String[objects.size()];

                        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

                        HashMap<String, String> map = new HashMap<String, String>();
                        for(int i = 0; i < objects.size(); i++){
                            map.put( "elemento" , ((Element) objects.get(i)).getName() );
                            data.add(map);
                        }

                        adapterDetails = new SimpleAdapter(MainActivity.this, data, android.R.layout.simple_list_item_1, new String[] {"elemento"},
                                new int[] { android.R.id.text1 });
                        holderDetails.list.setAdapter(adapterDetails);
                    }

                    ((ViewPager) container).addView(holderDetails.wrap, POSITION_DETAILS);
                    return holderDetails.wrap;
                case POSITION_RELATED:
                    Log.v(TAG, "POSITION_RELATED");
                    if(holderRelated == null){
                        holderRelated = new HolderRelated();
                        holderRelated.wrap = (ViewGroup) mLayoutInflater.inflate(R.layout.viewpager_page, null);
                        holderRelated.list = (ListView) holderRelated.wrap.findViewById(R.id.list);
                        container.setTag(holderRelated);
                    }

                    if(adaterRelated == null){
                        ArrayList<Object> objects = values.get(POSITION_RELATED);
                        String[] items = new String[objects.size()];

                        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

                        HashMap<String, String> map = new HashMap<String, String>();
                        for(int i = 0; i < objects.size(); i++){
                            map.put( "elemento" , ((Element) objects.get(i)).getName() );
                            data.add(map);
                        }

                        adaterRelated = new SimpleAdapter(MainActivity.this, data, android.R.layout.simple_list_item_1, new String[] {"elemento"},
                                new int[] { android.R.id.text1 });
                        holderRelated.list.setAdapter(adaterRelated);
                    }
                    ((ViewPager) container).addView(holderRelated.wrap, POSITION_RELATED);
                    return holderRelated.wrap;
                case POSITION_COMMENTS:
                    Log.v(TAG, "POSITION_COMMENTS");
                    if(holderComments == null){
                        holderComments = new HolderComments();
                        holderComments.wrap = (ViewGroup) mLayoutInflater.inflate(R.layout.viewpager_page, null);
                        holderComments.list = (ListView) holderComments.wrap.findViewById(R.id.list);
                        container.setTag(holderComments);
                    }

                    if(adaptercomments == null){
                        ArrayList<Object> objects = values.get(POSITION_COMMENTS);
                        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

                        HashMap<String, String> map = new HashMap<String, String>();
                        for(int i = 0; i < objects.size(); i++){
                            map.put( "elemento" , ((Element) objects.get(i)).getName() );
                            data.add(map);
                        }

                        adaptercomments = new SimpleAdapter(MainActivity.this, data, android.R.layout.simple_list_item_1, new String[] {"elemento"},
                                new int[] { android.R.id.text1 });
                        holderComments.list.setAdapter(adaptercomments);
                    }
                    ((ViewPager) container).addView(holderComments.wrap, POSITION_COMMENTS);
                    return holderComments.wrap;
            }

            return super.instantiateItem(container, position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ViewGroup) object);
        }


    }

    static class HolderDetails{
        ViewGroup wrap;
        ListView list;
    }
    static class HolderRelated{
        ViewGroup wrap;
        ListView list;
    }
    static class HolderComments{
        ViewGroup wrap;
        ListView list;
    }

    class Element{
        private int id;
        private String name;

        public Element(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
