import { TestBed } from '@angular/core/testing';

import { GrammarModuleService } from './grammar-module.service';

describe('GrammarModuleService', () => {
  let service: GrammarModuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrammarModuleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
