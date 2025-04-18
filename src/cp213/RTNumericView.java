package cp213;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * View and update the right triangle model with numeric fields.
 *
 * @author Giuseppe Akbari
 * @author David Brown from Byron Weber-Becker
 * @version 2024-20-11
 */
@SuppressWarnings("serial")
public class RTNumericView extends JPanel {

	/**
	 * An inner class that uses a FocusListener to access the numeric field. It sets
	 * the model values when the field loses focus.
	 */
	private class BaseFieldListener implements FocusListener {
		// Automatically highlight the entire contents of the numeric field.
		@Override
		public void focusGained(final FocusEvent evt) {
			RTNumericView.this.base.selectAll();
		}

		@Override
		public void focusLost(final FocusEvent evt) {
			double value = 0;

			try {
				value = Double.parseDouble(RTNumericView.this.base.getText());
			} catch (final java.lang.NumberFormatException e) {
				value = RTModel.MAX_SIDE / 2;
			} finally {
				RTNumericView.this.model.setBase(value);
			}
		}
	}

	/**
	 * An inner class the updates the base and hypotenuse labels whenever the
	 * model's base attribute is updated.
	 */
	private class BaseListener implements PropertyChangeListener {

		@Override
		public void propertyChange(final PropertyChangeEvent evt) {
			RTNumericView.this.base.setText(DECIMAL_FORMAT.format(RTNumericView.this.model.getBase()));
			RTNumericView.this.hypo
					.setText(RTNumericView.DECIMAL_FORMAT.format(RTNumericView.this.model.getHypotenuse()));
		}
	}

	/**
	 * An inner class that uses a FocusListener to access the numeric field. It sets
	 * the model values when the field loses focus.
	 */
	private class HeightFieldListener implements FocusListener {
		// Automatically highlight the entire contents of the numeric field.
		@Override
		public void focusGained(final FocusEvent evt) {
			RTNumericView.this.height.selectAll();
		}

		@Override
		public void focusLost(final FocusEvent evt) {
			double value = 0;

			try {
				value = Double.parseDouble(RTNumericView.this.height.getText());
			} catch (final java.lang.NumberFormatException e) {
				value = RTModel.MAX_SIDE / 2;
			} finally {
				RTNumericView.this.model.setHeight(value);
			}
		}
	}

	/**
	 * An inner class the updates the height and hypotenuse labels whenever the
	 * model's height attribute is updated.
	 */
	private class HeightListener implements PropertyChangeListener {

		@Override
		public void propertyChange(final PropertyChangeEvent evt) {
			RTNumericView.this.height.setText(DECIMAL_FORMAT.format(RTNumericView.this.model.getHeight()));
			RTNumericView.this.hypo
					.setText(RTNumericView.DECIMAL_FORMAT.format(RTNumericView.this.model.getHypotenuse()));
		}
	}

	// -------------------------------------------------------------------------------
	/**
	 * The format string for reading / displaying numeric input / output.
	 */
	private static final String FORMAT_STRING = "###.##";
	/**
	 * The formatters for reading / displaying numeric input / output.
	 */
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(FORMAT_STRING);
	/**
	 * The base value field.
	 */
	private final JTextField base = new JTextField(FORMAT_STRING.length());
	/**
	 * The height value field.
	 */
	private final JTextField height = new JTextField(FORMAT_STRING.length());
	/**
	 * The hypotenuse value field - cannot be edited by the user.
	 */
	private final JLabel hypo = new JLabel(" ");
	/**
	 * The right triangle model.
	 */
	private final RTModel model;

	/**
	 * The view constructor.
	 *
	 * @param model The right triangle model to view.
	 */
	public RTNumericView(final RTModel model) {
		this.model = model;
		this.layoutView();
		this.registerListeners();
		// Initialize the view labels.
		this.base.setText(DECIMAL_FORMAT.format(this.model.getBase()));
		this.height.setText(DECIMAL_FORMAT.format(this.model.getHeight()));
		this.hypo.setText(RTNumericView.DECIMAL_FORMAT.format(this.model.getHypotenuse()));
	}

	/**
	 * Uses the BoxLayout to place the labels and numeric fields.
	 */
	private void layoutView() {
		// Define the panel border.
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// Define the widgets.
		this.base.setHorizontalAlignment(SwingConstants.RIGHT);
		this.height.setHorizontalAlignment(SwingConstants.RIGHT);
		this.hypo.setHorizontalAlignment(SwingConstants.RIGHT);
		// Lay out the panel.
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JLabel("Base: "));
		this.add(this.base);
		this.add(new JLabel("Height: "));
		this.add(this.height);
		this.add(new JLabel("Hypotenuse: "));
		this.add(this.hypo);
	}

	/**
	 * Assigns listeners to the field widgets and the model.
	 */
	private void registerListeners() {
		// Add widget listeners.
		this.base.addFocusListener(new BaseFieldListener());
		this.height.addFocusListener(new HeightFieldListener());
		// Add model listeners.
		this.model.addPropertyChangeListener(RTModel.Type.BASE, new BaseListener());
		this.model.addPropertyChangeListener(RTModel.Type.HEIGHT, new HeightListener());
	}
}