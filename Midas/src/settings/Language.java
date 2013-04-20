﻿/* ============================================================================
 * Nom du fichier   : Language.java
 * ============================================================================
 * Date de création : 10 avr. 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package settings;

import core.MidasLogs;
import utils.XMLGetters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


/**
 * TODO
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class Language {
   
   private static final String xmlRootName = "language";
   
   public enum Text {
      /* Déclarations des textes, ajouter les noms des champs de texte
       * nécessaires.
       */
      APP_TITLE,
      ACTION_QUIT,
      
      VALIDATE_BUTTON,
      CANCEL_BUTTON,
      ACCOUNT_NAME_LABEL;
      
      
      /* Fin des déclarations, ne pas modifier ci-dessous
       */
      
      private String text = "missing String";
      
      @Override
      public String toString(){
         return text;
      }
      
      private String getXMLName(){
         return super.toString().toLowerCase();
      }
      
      private void setValue(String text) {
         this.text = text;
      }
      
   }
   
   public static void loadFromFile(File languageFile) {
      
      MidasLogs.messages.push("Language", "Start loading texts from " + languageFile.getAbsolutePath());
      
      if (languageFile.exists()) {  
         Document document = null;
         SAXBuilder saxBuilder = new SAXBuilder();
         
         Element root;
         
         try {
            document = saxBuilder.build(languageFile);
         }
         catch(Exception e) {
            MidasLogs.errors.push("Language", "Unable to load the file \"" + languageFile.getName() + "\".");
         }
         
         // Reconfiguration du type enum en lisant le fichier de langue
         String readValue;
         if (document != null) {
            root = document.getRootElement();
            
            for (Text text : Text.values()) {
               readValue = XMLGetters.getStringChild(root, text.getXMLName());
               
               // Ne modifie que si le texte contient au moins un caractère
               if (!readValue.isEmpty()) {
                  text.setValue(readValue);
               }
               
            }
         }         
      }
      else {
         MidasLogs.errors.push("Language", "File \"" + languageFile.getName() + "\" does not exist.");
      }
      
   }
   
   public static void createTemplateLanguageFile(File languageFile) {
      
      try {
         if(!languageFile.getParentFile().exists()) {
            languageFile.getParentFile().mkdirs();
         }
      }
      catch(Exception e) {
         MidasLogs.errors.push("Settings", "Unable to create language folder.");
      }
      
      Element root = new Element(xmlRootName);
      Document document = new Document(root);
      
      Element textElement;
      for (Text text : Text.values()) {
         textElement = new Element(text.getXMLName());
         textElement.setText(" TODO ");
         
         root.addContent(textElement);
      }
      
      /* Creation du fichier à l'emplacement voulu
       * ----------------------------------------------------------------------
       */
      FileOutputStream fileOutputStream = null; 
      try {
         fileOutputStream = new FileOutputStream(languageFile);
         
         XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
         
         outputter.output(document, fileOutputStream);
      }
      catch (IOException ex) {
         MidasLogs.errors.push("Language",
                               "Unable to create template file for language.");
      }
      finally {
         if (fileOutputStream != null) {
            try {
               fileOutputStream.close();
            }
            catch (IOException ex) {
               MidasLogs.errors.push("Language",
                                     "Error while closing the output stream.");
            }
         }
      }
      
   }

}
