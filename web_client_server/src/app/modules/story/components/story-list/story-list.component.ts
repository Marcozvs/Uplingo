import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-story-list',
  imports: [CommonModule],
  templateUrl: './story-list.component.html'
})
export class StoryListComponent {}
