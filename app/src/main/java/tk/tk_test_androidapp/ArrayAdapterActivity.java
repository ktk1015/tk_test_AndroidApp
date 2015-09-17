/**
 * Custom Array Adapter
 * related file: ItemEntry
 * 이름, 전화번호, 사진 정보들을 ArrayList && ArrayAdapter를 이용하여 커스텀 리스트뷰 UI 구현
 */

package tk.tk_test_androidapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArrayAdapterActivity extends ListActivity {

    ArrayAdapter<ItemEntry> itemAdapter;
    final ArrayList<ItemEntry> itemList= new ArrayList<ItemEntry>();
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter);

        ItemEntry ne1= new ItemEntry("Miki","0101235532",R.drawable.photo1);
        itemList.add(ne1);
        ItemEntry ne2= new ItemEntry("Aki","010443295520",R.drawable.photo2);
        itemList.add(ne2);
        ItemEntry ne3= new ItemEntry("Snoopy","0104872959278",R.drawable.photo3);
        itemList.add(ne3);
        ItemEntry ne4= new ItemEntry("July","010573965927",R.drawable.photo4);
        itemList.add(ne4);

        itemAdapter = new ItemArrayAdapter(this, R.layout.entry, R.id.eName, itemList);
        setListAdapter(itemAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final int index = position;
        final ItemEntry ent = itemAdapter.getItem(position);
        final String pn = ent.getPhoneNo();
        final int _photo = ent.getPhotoId()!=-1?ent.getPhotoId():R.drawable.nophoto;

        new AlertDialog.Builder(this).setTitle(ent.getName())
                .setMessage(pn)
                .setIcon(ent.getPhotoId())
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemAdapter.remove(ent);
                        itemAdapter.notifyDataSetChanged();
                    }
                })
                .setNeutralButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+pn));
                        startActivity(i);
                    }
                }).show();
    }//

    private class ItemArrayAdapter extends ArrayAdapter<ItemEntry>
    {
        private ArrayList<ItemEntry> items;
        private int rsrc;

        public ItemArrayAdapter(Context ctx, int rsrcId, int txtId, ArrayList<ItemEntry>data){
            super(ctx,rsrcId,txtId,data);
            this.items = data;
            this.rsrc = rsrcId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v==null){
                LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(rsrc,null);
            }

            ItemEntry e = items.get(position);
            if(e !=null){
                ((TextView)v.findViewById(R.id.eName)).setText(e.getName());
                ((TextView)v.findViewById(R.id.ePhoneNo)).setText(e.getPhoneNo());

                if(e.getPhotoId() !=-1){
                    ((ImageView)v.findViewById(R.id.ePhoto)).setImageResource(e.getPhotoId());
                }
                else
                {
                    ((ImageView)v.findViewById(R.id.ePhoto)).setImageResource(R.drawable.nophoto);
                }
            }
            return v;
        }
    }

}
