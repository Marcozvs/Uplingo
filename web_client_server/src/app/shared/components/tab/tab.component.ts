import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ITabItem } from '@shared/interfaces/tab.interfaces';
import { CardComponent } from '@shared/components/card/card.component';

@Component({
  selector: 'app-tab',
  standalone: true,
  imports: [CommonModule, CardComponent],
  templateUrl: './tab.component.html',
})
export class TabComponent {
  @Input() tabs: ITabItem[] = [];
  @Input() activeTabId?: string;

  @Output() tabChange = new EventEmitter<ITabItem>();

  setActive(tab: ITabItem): void {
    if (!tab.disabled) {
      this.activeTabId = tab.id;
      this.tabChange.emit(tab);
    }
  }

  isActive(tab: ITabItem): boolean {
    return tab.id === this.activeTabId;
  }
}
