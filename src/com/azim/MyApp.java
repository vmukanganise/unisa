package com.azim;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MyApp extends Activity implements OnClickListener
{
	//VMukanganise Assignment
	EditText editassetno,editName,dtpurchtxt, loctxt, owntxt, deptxt, pvaltxt;
	Button btnAdd,btnDelete,btnModify,btnView,btnViewAll,btnShowInfo;
	SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editassetno=(EditText)findViewById(R.id.editassetno);
        editName=(EditText)findViewById(R.id.editName);
        dtpurchtxt=(EditText)findViewById(R.id.dtpurchtxt);
        loctxt = (EditText)findViewById(R.id.loctxt);
        owntxt = (EditText)findViewById(R.id.owntxt);
        deptxt = (EditText)findViewById(R.id.deptxt);
        pvaltxt = (EditText)findViewById(R.id.pvaltxt);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnModify=(Button)findViewById(R.id.btnModify);
        btnView=(Button)findViewById(R.id.btnView);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);
        btnShowInfo=(Button)findViewById(R.id.btnShowInfo);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnShowInfo.setOnClickListener(this);
        db=openOrCreateDatabase("AssetDB", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS Assets(id VARCHAR,name VARCHAR,location VARCHAR, owner VARCHAR, dt_purchased VARCHAR, depreciation VARCHAR, purchased_value VARCHAR, current_value VARCHAR);");
    }
    public void onClick(View view)
    {
    	if(view==btnAdd)
    	{
    		if(editassetno.getText().toString().trim().length()==0||
    		   editName.getText().toString().trim().length()==0||
    		   dtpurchtxt.getText().toString().trim().length()==0||
    		   loctxt.getText().toString().trim().length()==0||
    		   owntxt.getText().toString().trim().length()==0||
    		   deptxt.getText().toString().trim().length()==0||
    		   pvaltxt.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter all values");
    			return;
    		}
    		db.execSQL("INSERT INTO Assets VALUES('"+editassetno.getText()+"','"+editName.getText()+
    				   "','"+loctxt.getText()+ "','"+owntxt.getText()+"','" +dtpurchtxt.getText() + "','"+deptxt.getText() +"','" + pvaltxt.getText() + "','"+pvaltxt.getText()+"');");
    		showMessage("Success", "Asset added");
    		clearText();
    	}
    	if(view==btnDelete)
    	{
    		if(editassetno.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter Asset Code");
    			return;
    		}
    		Cursor c=db.rawQuery("SELECT * FROM Assets WHERE id='"+editassetno.getText()+"'", null);
    		if(c.moveToFirst())
    		{
    			db.execSQL("DELETE FROM Assets WHERE id='"+editassetno.getText()+"'");
    			showMessage("Success", "Asset Deleted");
    		}
    		else
    		{
    			showMessage("Error", "Invalid Asset Code");
    		}
    		clearText();
    	}
    	if(view==btnModify)
    	{
    		if(editassetno.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter Asset Code");
    			return;
    		}
    		Cursor c=db.rawQuery("SELECT * FROM Assets WHERE id='"+editassetno.getText()+"'", null);
    		if(c.moveToFirst())
    		{
    			db.execSQL("UPDATE Assets SET name='"+editName.getText()+
    					"',dt_purchased='"+dtpurchtxt.getText()+"',location ='" +loctxt.getText()+
    					"',owner = '"+owntxt.getText() +
    					"',depreciation = '" + deptxt.getText()+
    					"',purchased_value = '"+pvaltxt.getText()+
    					"' WHERE id='"+editassetno.getText()+"'");
    			showMessage("Success", "Record Modified");
    		}
    		else
    		{
    			showMessage("Error", "Invalid Rollno");
    		}
    		clearText();
    	}
    	if(view==btnView)
    	{
    		if(editassetno.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter Asset Code");
    			return;
    		}
    		Cursor c=db.rawQuery("SELECT * FROM Assets WHERE id='"+editassetno.getText()+"'", null);
    		if(c.moveToFirst())
    		{
    			editName.setText(c.getString(1));
    			dtpurchtxt.setText(c.getString(4));
    			loctxt.setText(c.getString(2));
    			owntxt.setText(c.getString(3));
    			deptxt.setText(c.getString(5));
    			pvaltxt.setText(c.getString(6));
    			
    			
    		}
    		else
    		{
    			showMessage("Error", "Invalid Asset Code");
    			clearText();
    		}
    	}
    	if(view==btnViewAll)
    	{
    		Cursor c=db.rawQuery("SELECT * FROM Assets", null);
    		if(c.getCount()==0)
    		{
    			showMessage("Error", "No records found");
    			return;
    		}
    		StringBuffer buffer=new StringBuffer();
    		while(c.moveToNext())
    		{
    			buffer.append("Asset Code: "+c.getString(0)+"\n");
    			buffer.append("Name: "+c.getString(1)+"\n");
    			buffer.append("Purchased On: "+c.getString(4)+"\n\n");
    			buffer.append("Location: "+c.getString(2)+"\n");
    			buffer.append("Owner: "+c.getString(3)+"\n");
    			buffer.append("Purchase Value: "+c.getString(6)+"\n");
    			buffer.append("Depreciation: "+c.getString(5)+"\n");
    			buffer.append("Current Value: "+c.getString(7)+"\n");
    			
    		}
    		showMessage("Asset Details", buffer.toString());
    	}
    	if(view==btnShowInfo)
    	{
			showMessage("Asset Tracker Application", "Developed By VMukanganise");
    	}
    }
    public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
	}
    public void clearText()
    {
    	editassetno.setText("");
    	editName.setText("");
    	dtpurchtxt.setText("");
    	editassetno.requestFocus();
    	loctxt.setText("");
    	owntxt.setText("");
    	deptxt.setText("");
    	pvaltxt.setText("");
    }
}