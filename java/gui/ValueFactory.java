package gui;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.IRuntimeState;
import org.overture.interpreter.runtime.Interpreter;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.values.BooleanValue;
import org.overture.interpreter.values.CharacterValue;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.MapValue;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.NilValue;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.RealValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.SeqValue;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.TokenValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.ValueMap;
import org.overture.interpreter.values.ValueSet;
import org.overture.interpreter.values.VoidValue;

public class ValueFactory
{
	public static class ValueFactoryException extends Exception
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ValueFactoryException(String message)
		{
			super(message);
		}

		public ValueFactoryException(String message, Exception e)
		{
			super(message, e);
		}

	}

	/**
	 * Interpreter used to look up types
	 */
	private Interpreter interpreter;
	public final Context context;

	/**
	 * Default constructor which sets the interpreter used to look up types
	 */
	public ValueFactory()
	{
		this.interpreter = Interpreter.getInstance();
		// This is how I got the assistantFactory for this class
		
		this.context = (Context) this.interpreter.initialContext.getGlobal();
		/*Iterator<Entry<ILexNameToken, Value>> it = this.context.entrySet().iterator();
		while(it.hasNext()){
			Entry<ILexNameToken, Value> temp = it.next();
			System.out.println(temp.getKey() + " " + temp.getValue());
		}
		System.out.println("oveeerrr");*/
		
		//SClassDefinition s = this.interpreter.findClass("Stratego");
		//this.context = this.interpreter.getAssistantFactory().createSClassDefinitionAssistant().getStatics(s);
	}

	public static BooleanValue create(boolean b) throws ValueFactoryException
	{
		return new BooleanValue(b);
	}

	public static CharacterValue create(char c) throws ValueFactoryException
	{
		return new CharacterValue(c);
	}

	public static IntegerValue create(int c) throws ValueFactoryException
	{
		return new IntegerValue(c);
	}

	public static SeqValue create(String string) throws ValueFactoryException
	{
		return new SeqValue(string);
	}

	public static NilValue createNil()
	{
		return new NilValue();
	}

	public static VoidValue createVoid()
	{
		return new VoidValue();
	}

	public static RealValue create(double c) throws ValueFactoryException
	{
		try
		{
			return new RealValue(c);
		} catch (Exception e)
		{
			throw new ValueFactoryException(e.getMessage(), e);
		}
	}

	public static QuoteValue createQuote(String quote)
	{
		return new QuoteValue(quote);
	}

	public static TokenValue createToken(Value token)
	{
		return new TokenValue(token);
	}

	public SetValue createSet(Collection<Value> collection)
	{
		ValueSet vList = new ValueSet();
		vList.addAll(collection);
		return new SetValue(vList);
	}

	public SeqValue createSeq(Collection<Value> collection)
	{
		ValueList vList = new ValueList();
		vList.addAll(collection);
		return new SeqValue(vList);
	}

	public RecordValue createRecord(String recordName, Object... fields)
			throws ValueFactoryException
	{
		List<Value> values = new Vector<Value>();
		for (Object object : fields)
		{
			if (object instanceof Boolean)
			{
				values.add(create((Boolean) object));
			} else if (object instanceof Character)
			{
				values.add(create((Character) object));
			} else if (object instanceof Integer)
			{
				values.add(create((Integer) object));
			} else if (object instanceof Double)
			{
				values.add(create((Double) object));
			} else if (object instanceof String)
			{
				values.add(create((String) object));
			} else if (object instanceof Value)
			{
				values.add((Value) object);
			} else
			{
				throw new ValueFactoryException("The type of field "
						+ object
						+ " is not supported. Only basic types and Value are allowed.");
			}
		}

		return createRecord(recordName, values);
	}

	public RecordValue createRecord(String recordName, Value... fields)
			throws ValueFactoryException
	{

		PType type = null;//interpreter.findType(recordName);
		String[] str = recordName.split("`");
		LinkedList<PDefinition> l = interpreter.findClass(str[0]).getDefinitions();
		for(PDefinition d : l){
			if(d.getName().toString().equals(str[1])){
				type = d.getType();
				break;
			}
			
		}
		if (type != null & type instanceof ARecordInvariantType)
		{
			ARecordInvariantType rType = (ARecordInvariantType) type;
			if (fields.length != rType.getFields().size())
			{
				throw new ValueFactoryException("Fileds count do not match record field count");
			}
			NameValuePairList list = new NameValuePairList();
			for (int i = 0; i < rType.getFields().size(); i++)
			{
				list.add(rType.getFields().get(i).getTagname(), fields[i]);
			}
			return new RecordValue(rType, list, context); // add the context here as argument.
		}
		throw new ValueFactoryException("Record " + recordName + " not found");
	}

	public MapValue createMap(ValueMap map)
	{
		return new MapValue(map);
	}

	public TupleValue createTuple(Value... fields)
	{
		ValueList list = new ValueList();
		for (Value value : fields)
		{
			list.add(value);
		}
		return new TupleValue(list);
	}
}
