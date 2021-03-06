package com.ui.component.button;

import com.ui.component.UIKeyword;
import com.ui.component.ComponentFactory;
import com.ui.component.MainFrame;

import javax.swing.*;

/**
 * ボタンを生成するためのボタンファクトリークラス。
 * ボタンの配属先によって分類、
 * トップ、マッチング、ロビー、ゲームパネルや、ダイアログなど、
 * 必要に応じて追加可能。
 */

public class ButtonFactory implements ComponentFactory {
    @Override
    public JLabel getLabel(UIKeyword labelType, String label) {
        return null;
    }

    @Override
    public JButton getButton(UIKeyword buttonType, String text) {
        return switch (buttonType) {
            case DialogButton -> new DialogButton(text);
            case TopPanelButton -> new TopPanelButton(text);
            case MatchingPanelButton -> new MatchingPanelButton(text);
            case LobbyPanelButton -> new LobbyPanelButton(text);
            case GamePanelButton -> new GamePanelButton(text);
            default -> throw new IllegalArgumentException("Unexpected value: " + buttonType);
        };
    }
}
