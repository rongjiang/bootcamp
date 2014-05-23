package codepath.demos.helloworlddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends Activity {

	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		String text = getIntent().getStringExtra("text");
		position = getIntent().getIntExtra("position", 0);
		int code = getIntent().getIntExtra("code", 0);
		// display the text in the editable field
		EditText etText = (EditText) findViewById(R.id.editTxt);
		etText.setText(text, TextView.BufferType.NORMAL);
		// move cursor to the end of the text
		etText.setSelection(etText.getText().length());
	}

	public void onSubmit(View v) {
	  EditText etText = (EditText) findViewById(R.id.editTxt);
	  // Prepare data intent 
	  Intent data = new Intent();
	  // Pass relevant data back as a result
	  data.putExtra("text", etText.getText().toString());
	  data.putExtra("position", position);
	  // Activity finished ok, return the data
	  setResult(RESULT_OK, data); // set result code and bundle data for response
	  finish(); // closes the activity, pass data to parent
	} 
		

}
