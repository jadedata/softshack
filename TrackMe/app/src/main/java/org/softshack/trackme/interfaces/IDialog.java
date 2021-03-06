package org.softshack.trackme.interfaces;


import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface IDialog {
    DefaultEvent<EventArgs> getOnYearChanged();

    String getYear();

    void show();
}
