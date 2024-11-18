package com.fardan7eghlim.jahat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.List;

public class CustomAdapterLinks extends BaseAdapter{

    private Context context;
    private List<ChatObj> result;
    private static LayoutInflater inflater=null;
    private RadioButton radioRepButton;
    private boolean flag=false;
    private boolean notCurentUser=false;

    public CustomAdapterLinks(SearchResultActivity SearchResultActivity, List<ChatObj> listOfChatObj) {
        // TODO Auto-generated constructor stub
        result=listOfChatObj;
        context=SearchResultActivity;
        inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        LinearLayout bg;
        TextView numberMember_tv;
        TextView type_tv;
        TextView title_tv;
        TextView address_tv;
        TextView comment_tv;
        ImageView avatar;

        public Holder(LinearLayout bg,TextView numberMember_tv, TextView type_tv,  TextView title_tv, TextView address_tv, TextView comment_tv, ImageView avatar) {
            this.numberMember_tv = numberMember_tv;
            this.type_tv = type_tv;
            this.title_tv = title_tv;
            this.address_tv = address_tv;
            this.comment_tv = comment_tv;
            this.avatar = avatar;
            this.bg=bg;
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder=new Holder((LinearLayout) new LinearLayout(context.getApplicationContext()),(TextView)new TextView(context.getApplicationContext()),  (TextView) new TextView(context.getApplicationContext()), (TextView) new TextView(context.getApplicationContext()), (TextView) new TextView(context.getApplicationContext()), (TextView) new TextView(context.getApplicationContext()), (ImageView) new ImageView(context.getApplicationContext()));
        final View rowView;
        rowView = inflater.inflate(R.layout.item_row, null);
        holder.numberMember_tv= (TextView) rowView.findViewById(R.id.numberMember_tx02);
        holder.type_tv= (TextView) rowView.findViewById(R.id.type_tx02);
        holder.title_tv= (TextView) rowView.findViewById(R.id.title_tx02);
        holder.address_tv= (TextView) rowView.findViewById(R.id.address_tx02);
        holder.comment_tv= (TextView) rowView.findViewById(R.id.comment_tx02);
        holder.avatar= (ImageView) rowView.findViewById(R.id.avatar_img);
        holder.bg= (LinearLayout) rowView.findViewById(R.id.bgOfRow);


        if(!result.get(position).getNumberOFmember().equals("unknown")) {
            holder.numberMember_tv.setText(result.get(position).getNumberOFmember());
        }else{
            holder.numberMember_tv.setVisibility(View.GONE);
            TextView numberMember_tx01= (TextView) rowView.findViewById(R.id.numberMember_tx01);
            numberMember_tx01.setVisibility(View.GONE);
        }
        final int typeId=Integer.parseInt(result.get(position).getType());
        holder.type_tv.setText(new Utility().convertType(typeId));
        holder.title_tv.setText(result.get(position).getName());
        holder.address_tv.setText(result.get(position).getLink());


        Bitmap temp_bitmap=result.get(position).getAvatar();
        if(temp_bitmap!=null)
            holder.avatar.setImageBitmap(temp_bitmap);
        else
            holder.avatar.setImageResource(R.drawable.no_avatar);


        holder.comment_tv.setText(result.get(position).getDescription());
        holder.comment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d= new Dialog(context);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                d.setContentView(R.layout.description);
                TextView moreDescription= (TextView) d.findViewById(R.id.moreDescription);
                moreDescription.setText(result.get(position).getDescription());
                Bitmap temp_bitmap=result.get(position).getAvatar();
                ImageView descriptAvatar= (ImageView) d.findViewById(R.id.descript_avatar);
                if(temp_bitmap!=null){
                    descriptAvatar.setVisibility(View.VISIBLE);
                    descriptAvatar.setImageBitmap(temp_bitmap);
                }else {
                    descriptAvatar.setVisibility(View.GONE);
                }
                Button desc_open= (Button) d.findViewById(R.id.btnOpenByTelegram);
                desc_open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Utility(context).goToUrl ( typeId==2?result.get(position).getLink():"https://telegram.me/"+result.get(position).getLink());
                    }
                });
                Button desc_share= (Button) d.findViewById(R.id.btnShareIt);
                desc_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s=result.get(position).getName()+"   ";
                        if(typeId==2)
                            s=s+result.get(position).getLink();
                        else
                            s=s+"https://telegram.me/"+result.get(position).getLink();
                        shareIt(context,s);
                    }
                });
                Button desc_reportIt= (Button) d.findViewById(R.id.btnReportIt);
                desc_reportIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      reportIt(context,result.get(position).getId());
                    }
                });
                d.show();
            }
        });



        //hide number for bot and group
        if(typeId!=1){
            holder.numberMember_tv.setVisibility(View.GONE);
            TextView temp= (TextView) rowView.findViewById(R.id.numberMember_tx01);
            temp.setVisibility(View.GONE);
        }

        //clickable
        holder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Utility(context).goToUrl ( typeId==2?result.get(position).getLink():"https://telegram.me/"+result.get(position).getLink());
            }
        });

        return rowView;
    }

    private void shareIt(Context p,String temp) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "link:"+temp+"  ShareLink!";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Link");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, temp);
        p.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void reportIt(Context p,String channelId) {
        Intent reportingIntent = new Intent( p, RepActivity.class);
        reportingIntent.putExtra("channel_id", channelId);
        context.startActivity(reportingIntent);
    }
}