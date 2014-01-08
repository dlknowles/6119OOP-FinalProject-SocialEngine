package socialengine.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays some information about the Social Engine application.
 */
public class AboutDialog extends JDialog
{
    public AboutDialog(final EnvironmentFrame parentFrame)
    {
        super(parentFrame, "About", true);

        this.parentFrame = parentFrame;
        wasPaused = parentFrame.getEnvironment().isPaused();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setSize(400, 300);
        setLayout(null);
        setResizable(false);

        String aboutMessage = 
            "<html>" +
            "<div style='text-align: center; padding: 5px; width: 290px; font-weight: normal;'>" +
            "<h1>Social Engine</h1>" +
            "SocialEngine is an application that was developed as a final " +
            "project for CPSC 6119 - Object Oriented Development. " +
            "SocialEngine demonstrates a virtual environment where people " +
            "respond to perceived events that occur randomly in the " +
            "environment." +
            "</div>" +
            "</html>";

        String appInfo = "<html>" +
            "<div style='text-align: center; padding: 5px; width: 290px; font-weight: normal;'>" +
            "<b>Version 1.0</b><br>" +
            "Developed by " +
            "Chris McCleary & Daniel Knowles" +
            "</div>" +
            "</html>";

        Insets ins = getInsets();
        

        JLabel messageLabel = new JLabel(aboutMessage);
        Dimension messageSize = messageLabel.getPreferredSize();
        messageLabel.setBounds(GAP + ins.left, GAP + ins.top,
                                messageSize.width, messageSize.height);
        add(messageLabel);
        
        JLabel infoLabel = new JLabel(appInfo);
        Dimension infoSize = infoLabel.getPreferredSize();
        infoLabel.setBounds(ins.left,
                           GAP + (messageLabel.getY() + messageSize.height),
                           infoSize.width, infoSize.height);
        add(infoLabel);

        JButton okButton = new JButton("OK");
        Dimension okSize = okButton.getPreferredSize();
        okButton.setBounds(ins.left + (this.getWidth() / 2 - (okSize.width / 2)),
                           GAP + (infoLabel.getY() + infoSize.height),
                           okSize.width, okSize.height);
        okButton.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent e) { dispose(); }
        });

        add(okButton);
    }

    @Override
    public void dispose()
    {
        parentFrame.getEnvironment().unPause();

        super.dispose();
    }

    EnvironmentFrame parentFrame;
    private static final int GAP = 10;
    boolean wasPaused;
}
