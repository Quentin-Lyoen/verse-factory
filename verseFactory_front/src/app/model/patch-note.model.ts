export interface PatchNoteCategory {
  type: 'feature' | 'improvement' | 'fix';
  title: string;
  items: string[];
}

export interface PatchNote {
  version: string;
  date: string;
  badge?: string;
  title: string;
  description: string;
  categories: PatchNoteCategory[];
}