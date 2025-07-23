import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { IToast } from '@shared/interfaces/toast.interfaces';
import { ToastSeverityEnum } from '@shared/enum/toast.enums';
import { CardComponent } from '@shared/components/card/card.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule, CardComponent],
  templateUrl: './toast.component.html',
})
export class ToastComponent implements OnChanges {
  @Input() toastQueue: IToast[] = [];

  localToasts: (IToast & { displayed: boolean })[] = [];

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['toastQueue'] && this.toastQueue.length) {
        this.localToasts = this.toastQueue.map(toast => ({
          ...toast,
          displayed: false,
        }));

        setTimeout(() => {
          this.localToasts.forEach(toast => (toast.displayed = true));
        }, 0);

        this.localToasts.forEach(toast => {
          setTimeout(() => {
            toast.displayed = false;
            setTimeout(() => {
              this.localToasts = this.localToasts.filter(t => t.id !== toast.id);
            }, 300);
          }, toast.duration || 3000);
        });
    }
  }


  getIconByType(severity: ToastSeverityEnum): string {
    switch (severity) {
      case ToastSeverityEnum.SUCCESS: return 'check-circle';
      case ToastSeverityEnum.ERROR: return 'close-circle';
      case ToastSeverityEnum.WARN: return 'alert-circle';
      case ToastSeverityEnum.INFO:
      default: return 'information';
    }
  }

  getBackgroundColor(severity: ToastSeverityEnum): string {
    switch (severity) {
      case ToastSeverityEnum.SUCCESS: return 'bg-green-600 dark:bg-green-700';
      case ToastSeverityEnum.ERROR: return 'bg-red-600 dark:bg-red-700';
      case ToastSeverityEnum.WARN: return 'bg-yellow-600 dark:bg-yellow-700';
      case ToastSeverityEnum.INFO:
      default: return 'bg-blue-600 dark:bg-blue-700';
    }
  }

  getIconColor(severity: ToastSeverityEnum): string {
    switch (severity) {
      case ToastSeverityEnum.SUCCESS: return 'text-green-300';
      case ToastSeverityEnum.ERROR: return 'text-red-300';
      case ToastSeverityEnum.WARN: return 'text-yellow-300';
      case ToastSeverityEnum.INFO:
      default: return 'text-blue-300';
    }
  }
}
