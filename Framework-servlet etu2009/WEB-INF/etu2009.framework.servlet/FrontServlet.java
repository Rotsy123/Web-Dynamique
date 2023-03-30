package etu2009.framework.servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls;
    public void init (){
        MappingUrls = new HashMap<>();
          try {
            String directory ="C://Users//Tsiky Aro//Documents//NetBeansProjects//s4//Mr Naina//Framework//WebDynamique//WebDynamique//src//java//ETU2035//framework//model";
            String [] classe = reset(directory);
            for(int i =0 ;i< classe.length; i++){
                 String className = classe[i];
                String name = classe[i];
                className = "ETU2035.framework.model." +className;
                Class<?> clazz;
                clazz = Class.forName(className);
                Method [] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                     Annotation[] an = method.getAnnotations();
                     if(an.length!=0){
                         GetUrl annotation = method.getAnnotation(GetUrl.class);
                         MappingUrls.put(annotation.url(),new Mapping(name,method.getName()));
                     }
                }
            }
         } catch (Exception ex) {
              ex.printStackTrace();
         }
    }
    public String[] reset(String Directory){
        ArrayList<String> rar=new ArrayList<>();
        File dossier = new File(Directory);
        String[] contenu = dossier.list();
        for(int i=0; i<contenu.length; i++){
            String fe=FilenameUtils.getExtension(contenu[i]);
            if(fe.equalsIgnoreCase("java")){
                String [] value = contenu[i].split("[.]");
                rar.add(value[0]);
            }
        }
       return rar.toArray(new String[rar.size()]); 
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String url=request.getServletPath();
        String requete=request.getQueryString();
        if (requete!=null) {
            url=url+"?"+requete;
        }
        request.setAttribute("url",url);
        RequestDispatcher dispat=request.getRequestDispatcher("url.jsp");
        dispat.forward(request,response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}