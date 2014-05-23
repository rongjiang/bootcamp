package codepath.demos.helloworlddemo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class HelloWorldActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int REQUEST_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);
		
		lvItems = (ListView) findViewById(R.id.lvItems);
		items = new ArrayList<String>();
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);;
		items.add("First Item");
		items.add("Second Item");
		setupListViewListener();
		
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, 
					View view, int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long rowId) {
				// open a new screen for editing
				launchEditView(items.get(position), position);
				
				itemsAdapter.notifyDataSetChanged();	            
				saveItems();
			}
		});
	}
	
	private void launchEditView(String text, int position) {
        Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);

        intent.putExtra("text", text);
        intent.putExtra("position", position);
        intent.putExtra("code", 400);
		
        startActivityForResult(intent, REQUEST_CODE);
	}

	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		return true;
	}

	public void addTodoItem(View v) {
		EditText etNewItem = (EditText)
				findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems(); // write to file
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract text from result extras
	     String text = data.getExtras().getString("text");
	     int position = data.getIntExtra("position", 0);
	     // display the updated text	     
	     items.set(position,  text);
	  }
	} 
}
