import Link from 'next/link';

export default function Home() {
  return (
    <>
      <header>
        <h1>
          <Link href={'/'}>ガチャApp</Link>
        </h1>
      </header>
    </>
  );
}
