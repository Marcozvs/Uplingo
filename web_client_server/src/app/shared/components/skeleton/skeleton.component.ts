import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-skeleton',
  imports: [CommonModule],
  templateUrl: './skeleton.component.html',
})
export class SkeletonComponent {}
