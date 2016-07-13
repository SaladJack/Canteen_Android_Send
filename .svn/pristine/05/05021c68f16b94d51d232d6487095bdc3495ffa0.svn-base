package com.kai.distribution.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kai.distribution.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Distributing extends Fragment
{
	private View view;
	private TextView area;
	private TextView service_time;
	private TextView show_count;
	private Spinner spinner;
	private ListView show_takeoutfood;
	private List<String> spinner_content;
	private ArrayAdapter<String> spinner_adapter;
	private ListView_Adapter listview_adapter;
	private final String[] spinner_text = { "北一", "北二", "北三", "北四", "北五", "北六",
			"北七", "北八", "北九", "北十", "北十一", "北十二", "北十三", "北十四", "北十五", "北十六",
			"北十七", "北十八", "北十九", "北二十" };

	private String current_area;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_disttributing, container, false);
		initView();
		return view;
	}

	private void initView() {
		area = (TextView) view.findViewById(R.id.area);
		service_time = (TextView) view.findViewById(R.id.service_time);
		show_count=(TextView) view.findViewById(R.id.show_count);
		spinner = (Spinner) view.findViewById(R.id.show_listview);
		spinner_content = new ArrayList<String>();
		for (int i = 0; i < spinner_text.length; i++) {
			spinner_content.add(spinner_text[i]);
		}
		spinner_adapter = new ArrayAdapter(getActivity(),
				R.layout.show_spinner_text,spinner_content);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		spinner.setAdapter(spinner_adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				current_area = (String) spinner.getSelectedItem();
				listview_adapter.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				

			}

		});

		show_takeoutfood = (ListView) view.findViewById(R.id.show_takeoutfood);
		listview_adapter = new ListView_Adapter(getActivity(), 10);
		show_takeoutfood.setAdapter(listview_adapter);
		
		int item_amount=show_takeoutfood.getCount();
		show_count.setText("配送中"+Integer.toString(item_amount)+")");
	}

	public class ListView_Adapter extends BaseAdapter {
		private Context context;
		private int i;

		public ListView_Adapter(Context context, int i)

		{
			this.context = context;
			this.i = i;
		}

		@Override
		public int getCount() {
			return i;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View view, ViewGroup viewgroup) {
			final ViewHolder holder;
			if (view == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				view = inflater.inflate(R.layout.takeoutfodd_content,
						viewgroup, false);
				holder = new ViewHolder();
				holder.which_area = (TextView) view.findViewById(R.id.which_area);
				holder.which_people = (TextView) view.findViewById(R.id.which_people);
				holder.is_out = (Button) view.findViewById(R.id.is_out);
				holder.is_confirm = (Button) view.findViewById(R.id.is_confirm);
				holder.delivered=(ImageButton) view.findViewById(R.id.delivered);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.which_area.setText(current_area);
			
			holder.is_out.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();  
					alertDialog.show();  
					Window window = alertDialog.getWindow();  
					window.setContentView(R.layout.confirm_out_dialog);  
					Button yes = (Button) window.findViewById(R.id.yes);  
					Button no = (Button) window.findViewById(R.id.no);  
					yes.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							holder.is_out.setVisibility(View.GONE);
							alertDialog.dismiss();
						}
					});
					no.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							alertDialog.dismiss();
						}
					});
				}
			});
			
			holder.is_confirm.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();  
					alertDialog.show();  
					Window window = alertDialog.getWindow();  
					window.setContentView(R.layout.confirm_dialog);  
					Button confirm=(Button) window.findViewById(R.id.confirm);
					confirm.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View view) {
							// TODO Auto-generated method stub
							holder.is_confirm.setVisibility(View.GONE);
							holder.delivered.setVisibility(View.VISIBLE);
							holder.delivered.setEnabled(false);
							alertDialog.dismiss();
						}
					});
				}
			});
			return view;
		}

	}

	static class ViewHolder {
		public TextView which_area, which_people;
		public Button is_out, is_confirm;
		public ImageButton delivered;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (listview_adapter != null) {
			listview_adapter.notifyDataSetChanged();
		}
	}
}
