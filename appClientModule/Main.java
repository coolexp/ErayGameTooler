import com.coolexp.ExcelTooler;
import com.coolexp.vo.InputArgsVO;


public class Main {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ExcelTooler.getInstance().parse(InputArgsVO.parse(args));
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}