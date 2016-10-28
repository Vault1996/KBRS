//package by.epam.cinemarating.filter;
//
//import javax.servlet.annotation.WebFilter;
//@WebFilter(	urlPatterns = { "/controller?command=edit_movie" })
//public class TokenFilter /*implements Filter*/ {
////	private static final String ENCODING = "encoding";
////	private String code;
////	public void init(FilterConfig fConfig) throws ServletException {
////		code = fConfig.getInitParameter(ENCODING);
////	}
////	public void doFilter(ServletRequest request, ServletResponse response,
////						 FilterChain chain) throws IOException, ServletException {
////		String codeRequest = request.getCharacterEncoding();
////// setting encoding to default encoding from init param if it is not exists
////		if (code != null && !code.equalsIgnoreCase(codeRequest)) {
////			request.setCharacterEncoding(code);
////			response.setCharacterEncoding(code);
////		}
////		chain.doFilter(request, response);
////	}
////	public void destroy() {
////		code = null;
////	}
//	//TODO: проверка токена;
//}
