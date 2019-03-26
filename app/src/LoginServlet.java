import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(name = "LoginServlet")

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public LoginServlet () {
    super();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    doGet(request, response);

//    response.setContentType("text/html");
//    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    // display plain html
    out.print("<html><body>");
    out.print("<h1>Hello No Login</h1>");
    out.print("</body></html>");

    out.close();
    /*
    if (request.getParameter("username") != null) {
      String username = request.getParameter("username");

      JSONArray jsArr = new JSONArray();
      try {
        jsArr.put(new JSONObject().put("username", username));
        jsArr.put(new JSONObject().put("username", "0000"));
      }
      catch (JSONException e) {
        e.printStackTrace();
      }
      RPCHelper.writeJsonArray(response, jsArr);
//      out.print(jsArr);

      /*
      // display json with parameter on webpage
      JSONObject jsobj = new JSONObject();
      try {
        jsobj.put("username", username);
      }
      catch (JSONException e) {
        e.printStackTrace();
      }
      out.print(jsobj);
      */

      /*
      // display html with parameter
      out.print("<html><body>");
      out.print("<h1>Hello " + username + " ! You've login </h1>");
      out.print("</body></html>");
      */
      /*
    }
    else {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      // display plain html
      out.print("<html><body>");
      out.print("<h1>Hello No Login</h1>");
      out.print("</body></html>");

      out.close();
    }
*/

  }
}
