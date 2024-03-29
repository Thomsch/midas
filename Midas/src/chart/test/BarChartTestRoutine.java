/* ============================================================================
 * Nom du fichier   : BarChartTestRoutine.java
 * ============================================================================
 * Date de création : 16.05.2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package chart.test;

import javax.swing.JFrame;

import chart.dataset.BarValue;
import chart.types.BarChart;

/**
 * Test du diagramme en barres.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class BarChartTestRoutine {

   /**
    * Le méthode principale démarrant le test.
    * 
    * @param args
    *           - non utilisé.
    */
   public static void main(String[] args) {

      BarValue[] BarValues = { new BarValue(0.5, "cat1"),
            new BarValue(1.0, "cat2") };

      BarChart barChart = new BarChart("title", BarValues);

      JFrame frame = new JFrame("Bar Chart");
      frame.getContentPane().add(barChart.getChartPanel());
      frame.pack();
      frame.setVisible(true);

      try {
         Thread.sleep(5000);
      }
      catch (InterruptedException e) {
      }

      barChart.getDataset().removeColumn(0);

   }

}
