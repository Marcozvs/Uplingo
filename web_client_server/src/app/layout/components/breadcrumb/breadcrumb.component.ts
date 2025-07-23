import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterModule, UrlSegment } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';

import { filter } from 'rxjs/operators';

interface Breadcrumb {
  key: string;
  url: string;
}

@Component({
  standalone: true,
  selector: 'app-breadcrumb',
  imports: [CommonModule, TranslateModule, RouterModule],
  templateUrl: './breadcrumb.component.html',
})
export class BreadcrumbComponent implements OnInit {
  breadcrumbs: Breadcrumb[] = [];

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.breadcrumbs = this.buildBreadcrumbs(this.route.root);
      });
  }

  private buildBreadcrumbs(route: ActivatedRoute, url: string = '', breadcrumbs: Breadcrumb[] = []): Breadcrumb[] {
    const children = route.children;

    for (const child of children) {
      const routeURL: string = child.snapshot.url.map((segment: UrlSegment) => segment.path).join('/');
      if (routeURL) url += `/${routeURL}`;

      const labelKey = child.snapshot.data['breadcrumb'];
      if (labelKey) {
        breadcrumbs.push({ key: labelKey, url });
      }

      return this.buildBreadcrumbs(child, url, breadcrumbs);
    }

    return breadcrumbs;
  }
}
