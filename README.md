extendedset_secompax
====================

Druid is a real-time big data analytic platform. Druid uses CONCISE in its bitmap index compression, a concise version of PLWAH.
While CONCISE is good at speed, the design space in bitmap index compression is still unknown and worth to explore. We complement SECOMPAX for Druid with CONCISE for experiemental tests.  

SECOMPAX is a new bitmap index encoding algorithm, which is the abbreviations of Scope-Extended COMPressed Adaptive indeX. 
SECOMPAX is a descent of COMPAX with new designed codebook.  

SECOMPAX performs better compression ratio and fast encoding speed compared with the state-of-the-art bitmap index compression algorithm, such as  WAH (Word-Aligned-Hybrid), PLWAH(Position list word aligned hybrid) and COMPAX (COMPressed Adaptive indeX).

If you feel interested in this work, please cite it as follows:
[1] Wen, Yuhao, et al. "SECOMPAX: A bitmap index compression algorithm." Computer Communication and Networks (ICCCN), 2014 23rd International Conference on. IEEE, 2014.

please visit URL: http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=6911838.
