  


<!DOCTYPE html>
<html>
  <head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# githubog: http://ogp.me/ns/fb/githubog#">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>OwncloudPie/owncloudpie_setup.sh at master · petrockblog/OwncloudPie · GitHub</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub" />
    <link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub" />
    <link rel="apple-touch-icon" sizes="57x57" href="/apple-touch-icon-114.png" />
    <link rel="apple-touch-icon" sizes="114x114" href="/apple-touch-icon-114.png" />
    <link rel="apple-touch-icon" sizes="72x72" href="/apple-touch-icon-144.png" />
    <link rel="apple-touch-icon" sizes="144x144" href="/apple-touch-icon-144.png" />
    <link rel="logo" type="image/svg" href="http://github-media-downloads.s3.amazonaws.com/github-logo.svg" />
    <link rel="xhr-socket" href="/_sockets">
    <meta name="msapplication-TileImage" content="/windows-tile.png">
    <meta name="msapplication-TileColor" content="#ffffff">

    
    
    <link rel="icon" type="image/x-icon" href="/favicon.ico" />

    <meta content="authenticity_token" name="csrf-param" />
<meta content="XzEFMvkNrrtcpjhtBuuAjhrW0B95vMlOOH9to7MjQX0=" name="csrf-token" />

    <link href="https://a248.e.akamai.net/assets.github.com/assets/github-122db8683276fb4b06926aae42cad86ef57dbf69.css" media="all" rel="stylesheet" type="text/css" />
    <link href="https://a248.e.akamai.net/assets.github.com/assets/github2-ab66d7f455e418ccfc86d9d7d4fbda986d7ef474.css" media="all" rel="stylesheet" type="text/css" />
    


      <script src="https://a248.e.akamai.net/assets.github.com/assets/frameworks-bafee0a321be765ed924edd4c746d8af68510845.js" type="text/javascript"></script>
      <script src="https://a248.e.akamai.net/assets.github.com/assets/github-bc406d708a590490fc073d6b7f25d4a5cbc853a1.js" type="text/javascript"></script>
      
      <meta http-equiv="x-pjax-version" content="69ac3a24fba9ac629b8e170103658a71">

        <link data-pjax-transient rel='permalink' href='/petrockblog/OwncloudPie/blob/0a99f4de95e3573247ab1600b211f3a890d1fd40/owncloudpie_setup.sh'>
    <meta property="og:title" content="OwncloudPie"/>
    <meta property="og:type" content="githubog:gitrepository"/>
    <meta property="og:url" content="https://github.com/petrockblog/OwncloudPie"/>
    <meta property="og:image" content="https://secure.gravatar.com/avatar/7087afbd4828c361e547117d667579bc?s=420&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png"/>
    <meta property="og:site_name" content="GitHub"/>
    <meta property="og:description" content="OwncloudPie - Shell script for installing Owncloud on the Raspberry Pi"/>
    <meta property="twitter:card" content="summary"/>
    <meta property="twitter:site" content="@GitHub">
    <meta property="twitter:title" content="petrockblog/OwncloudPie"/>

    <meta name="description" content="OwncloudPie - Shell script for installing Owncloud on the Raspberry Pi" />

  <link href="https://github.com/petrockblog/OwncloudPie/commits/master.atom" rel="alternate" title="Recent Commits to OwncloudPie:master" type="application/atom+xml" />

  </head>


  <body class="logged_out page-blob macintosh vis-public env-production  ">
    <div id="wrapper">

      

      
      
      

      
      <div class="header header-logged-out">
  <div class="container clearfix">

      <a class="header-logo-wordmark" href="https://github.com/">Github</a>

      <ul class="top-nav">
          <li class="explore"><a href="https://github.com/explore">Explore GitHub</a></li>
        <li class="search"><a href="https://github.com/search">Search</a></li>
        <li class="features"><a href="https://github.com/features">Features</a></li>
          <li class="blog"><a href="https://github.com/blog">Blog</a></li>
      </ul>

    <div class="header-actions">
        <a class="button primary" href="https://github.com/signup">Sign up for free</a>
      <a class="button" href="https://github.com/login?return_to=%2Fpetrockblog%2FOwncloudPie%2Fblob%2Fmaster%2Fowncloudpie_setup.sh">Sign in</a>
    </div>

  </div>
</div>


      

      


            <div class="site hfeed" itemscope itemtype="http://schema.org/WebPage">
      <div class="hentry">
        
        <div class="pagehead repohead instapaper_ignore readability-menu ">
          <div class="container">
            <div class="title-actions-bar">
              


<ul class="pagehead-actions">



    <li>
      <a href="/login?return_to=%2Fpetrockblog%2FOwncloudPie"
        class="minibutton js-toggler-target star-button entice tooltipped upwards"
        title="You must be signed in to use this feature" rel="nofollow">
        <span class="mini-icon mini-icon-star"></span>Star
      </a>
      <a class="social-count js-social-count" href="/petrockblog/OwncloudPie/stargazers">
        39
      </a>
    </li>
    <li>
      <a href="/login?return_to=%2Fpetrockblog%2FOwncloudPie"
        class="minibutton js-toggler-target fork-button entice tooltipped upwards"
        title="You must be signed in to fork a repository" rel="nofollow">
        <span class="mini-icon mini-icon-fork"></span>Fork
      </a>
      <a href="/petrockblog/OwncloudPie/network" class="social-count">
        10
      </a>
    </li>
</ul>

              <h1 itemscope itemtype="http://data-vocabulary.org/Breadcrumb" class="entry-title public">
                <span class="repo-label"><span>public</span></span>
                <span class="mega-icon mega-icon-public-repo"></span>
                <span class="author vcard">
                  <a href="/petrockblog" class="url fn" itemprop="url" rel="author">
                  <span itemprop="title">petrockblog</span>
                  </a></span> /
                <strong><a href="/petrockblog/OwncloudPie" class="js-current-repository">OwncloudPie</a></strong>
              </h1>
            </div>

            
  <ul class="tabs">
    <li><a href="/petrockblog/OwncloudPie" class="selected" highlight="repo_source repo_downloads repo_commits repo_tags repo_branches">Code</a></li>
    <li><a href="/petrockblog/OwncloudPie/network" highlight="repo_network">Network</a></li>
    <li><a href="/petrockblog/OwncloudPie/pulls" highlight="repo_pulls">Pull Requests <span class='counter'>0</span></a></li>

      <li><a href="/petrockblog/OwncloudPie/issues" highlight="repo_issues">Issues <span class='counter'>3</span></a></li>



    <li><a href="/petrockblog/OwncloudPie/graphs" highlight="repo_graphs repo_contributors">Graphs</a></li>


  </ul>
  
<div class="tabnav">

  <span class="tabnav-right">
    <ul class="tabnav-tabs">
          <li><a href="/petrockblog/OwncloudPie/tags" class="tabnav-tab" highlight="repo_tags">Tags <span class="counter blank">0</span></a></li>
    </ul>
    
  </span>

  <div class="tabnav-widget scope">


    <div class="select-menu js-menu-container js-select-menu js-branch-menu">
      <a class="minibutton select-menu-button js-menu-target" data-hotkey="w" data-ref="master">
        <span class="mini-icon mini-icon-branch"></span>
        <i>branch:</i>
        <span class="js-select-button">master</span>
      </a>

      <div class="select-menu-modal-holder js-menu-content js-navigation-container">

        <div class="select-menu-modal">
          <div class="select-menu-header">
            <span class="select-menu-title">Switch branches/tags</span>
            <span class="mini-icon mini-icon-remove-close js-menu-close"></span>
          </div> <!-- /.select-menu-header -->

          <div class="select-menu-filters">
            <div class="select-menu-text-filter">
              <input type="text" id="commitish-filter-field" class="js-filterable-field js-navigation-enable" placeholder="Filter branches/tags">
            </div>
            <div class="select-menu-tabs">
              <ul>
                <li class="select-menu-tab">
                  <a href="#" data-tab-filter="branches" class="js-select-menu-tab">Branches</a>
                </li>
                <li class="select-menu-tab">
                  <a href="#" data-tab-filter="tags" class="js-select-menu-tab">Tags</a>
                </li>
              </ul>
            </div><!-- /.select-menu-tabs -->
          </div><!-- /.select-menu-filters -->

          <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket css-truncate" data-tab-filter="branches">

            <div data-filterable-for="commitish-filter-field" data-filterable-type="substring">

                <div class="select-menu-item js-navigation-item js-navigation-target selected">
                  <span class="select-menu-item-icon mini-icon mini-icon-confirm"></span>
                  <a href="/petrockblog/OwncloudPie/blob/master/owncloudpie_setup.sh" class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target" data-name="master" rel="nofollow" title="master">master</a>
                </div> <!-- /.select-menu-item -->
            </div>

              <div class="select-menu-no-results">Nothing to show</div>
          </div> <!-- /.select-menu-list -->


          <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket css-truncate" data-tab-filter="tags">
            <div data-filterable-for="commitish-filter-field" data-filterable-type="substring">

            </div>

            <div class="select-menu-no-results">Nothing to show</div>

          </div> <!-- /.select-menu-list -->

        </div> <!-- /.select-menu-modal -->
      </div> <!-- /.select-menu-modal-holder -->
    </div> <!-- /.select-menu -->

  </div> <!-- /.scope -->

  <ul class="tabnav-tabs">
    <li><a href="/petrockblog/OwncloudPie" class="selected tabnav-tab" highlight="repo_source">Files</a></li>
    <li><a href="/petrockblog/OwncloudPie/commits/master" class="tabnav-tab" highlight="repo_commits">Commits</a></li>
    <li><a href="/petrockblog/OwncloudPie/branches" class="tabnav-tab" highlight="repo_branches" rel="nofollow">Branches <span class="counter ">1</span></a></li>
  </ul>

</div>

  
  
  


            
          </div>
        </div><!-- /.repohead -->

        <div id="js-repo-pjax-container" class="container context-loader-container" data-pjax-container>
          


<!-- blob contrib key: blob_contributors:v21:48227600bf5517458c7514e385af4a9e -->
<!-- blob contrib frag key: views10/v8/blob_contributors:v21:48227600bf5517458c7514e385af4a9e -->


<div id="slider">
    <div class="frame-meta">

      <p title="This is a placeholder element" class="js-history-link-replace hidden"></p>

        <div class="breadcrumb">
          <span class='bold'><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/petrockblog/OwncloudPie" class="js-slide-to" data-branch="master" data-direction="back" itemscope="url"><span itemprop="title">OwncloudPie</span></a></span></span><span class="separator"> / </span><strong class="final-path">owncloudpie_setup.sh</strong> <span class="js-zeroclipboard zeroclipboard-button" data-clipboard-text="owncloudpie_setup.sh" data-copied-hint="copied!" title="copy to clipboard"><span class="mini-icon mini-icon-clipboard"></span></span>
        </div>

      <a href="/petrockblog/OwncloudPie/find/master" class="js-slide-to" data-hotkey="t" style="display:none">Show File Finder</a>


        
  <div class="commit file-history-tease">
    <img class="main-avatar" height="24" src="https://secure.gravatar.com/avatar/62b8be5879a184d24b5eeae64229a9de?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="24" />
    <span class="author"><span rel="author">petrockblog</span></span>
    <time class="js-relative-date" datetime="2013-04-06T07:35:11-07:00" title="2013-04-06 07:35:11">April 06, 2013</time>
    <div class="commit-title">
        <a href="/petrockblog/OwncloudPie/commit/0a99f4de95e3573247ab1600b211f3a890d1fd40" class="message">Fixed permission issue of temporary uploads directory (issue</a> <a href="https://github.com/petrockblog/OwncloudPie/issues/22" class="issue-link" title="makes cache dir writable">#22</a><a href="/petrockblog/OwncloudPie/commit/0a99f4de95e3573247ab1600b211f3a890d1fd40" class="message">).</a>
    </div>

    <div class="participation">
      <p class="quickstat"><a href="#blob_contributors_box" rel="facebox"><strong>3</strong> contributors</a></p>
          <a class="avatar tooltipped downwards" title="petrockblog" href="/petrockblog/OwncloudPie/commits/master/owncloudpie_setup.sh?author=petrockblog"><img height="20" src="https://secure.gravatar.com/avatar/7087afbd4828c361e547117d667579bc?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="20" /></a>
    <a class="avatar tooltipped downwards" title="simonspa" href="/petrockblog/OwncloudPie/commits/master/owncloudpie_setup.sh?author=simonspa"><img height="20" src="https://secure.gravatar.com/avatar/dfb332a23256ab9df5226c4a744da6ec?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="20" /></a>
    <a class="avatar tooltipped downwards" title="thinking-aloud" href="/petrockblog/OwncloudPie/commits/master/owncloudpie_setup.sh?author=thinking-aloud"><img height="20" src="https://secure.gravatar.com/avatar/06e0f2ec4216e2ac0ec907abe030a652?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="20" /></a>


    </div>
    <div id="blob_contributors_box" style="display:none">
      <h2>Users on GitHub who have contributed to this file</h2>
      <ul class="facebox-user-list">
        <li>
          <img height="24" src="https://secure.gravatar.com/avatar/7087afbd4828c361e547117d667579bc?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="24" />
          <a href="/petrockblog">petrockblog</a>
        </li>
        <li>
          <img height="24" src="https://secure.gravatar.com/avatar/dfb332a23256ab9df5226c4a744da6ec?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="24" />
          <a href="/simonspa">simonspa</a>
        </li>
        <li>
          <img height="24" src="https://secure.gravatar.com/avatar/06e0f2ec4216e2ac0ec907abe030a652?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="24" />
          <a href="/thinking-aloud">thinking-aloud</a>
        </li>
      </ul>
    </div>
  </div>


    </div><!-- ./.frame-meta -->

    <div class="frames">
      <div class="frame" data-permalink-url="/petrockblog/OwncloudPie/blob/0a99f4de95e3573247ab1600b211f3a890d1fd40/owncloudpie_setup.sh" data-title="OwncloudPie/owncloudpie_setup.sh at master · petrockblog/OwncloudPie · GitHub" data-type="blob">

        <div id="files" class="bubble">
          <div class="file">
            <div class="meta">
              <div class="info">
                <span class="icon"><b class="mini-icon mini-icon-text-file"></b></span>
                <span class="mode" title="File Mode">file</span>
                  <span>369 lines (304 sloc)</span>
                <span>13.162 kb</span>
              </div>
              <div class="actions">
                <div class="button-group">
                      <a class="minibutton js-entice" href=""
                         data-entice="You must be signed in and on a branch to make or propose changes">Edit</a>
                  <a href="/petrockblog/OwncloudPie/raw/master/owncloudpie_setup.sh" class="button minibutton " id="raw-url">Raw</a>
                    <a href="/petrockblog/OwncloudPie/blame/master/owncloudpie_setup.sh" class="button minibutton ">Blame</a>
                  <a href="/petrockblog/OwncloudPie/commits/master/owncloudpie_setup.sh" class="button minibutton " rel="nofollow">History</a>
                </div><!-- /.button-group -->
              </div><!-- /.actions -->

            </div>
                <div class="data type-shell js-blob-data">
      <table cellpadding="0" cellspacing="0" class="lines">
        <tr>
          <td>
            <pre class="line_numbers"><span id="L1" rel="#L1">1</span>
<span id="L2" rel="#L2">2</span>
<span id="L3" rel="#L3">3</span>
<span id="L4" rel="#L4">4</span>
<span id="L5" rel="#L5">5</span>
<span id="L6" rel="#L6">6</span>
<span id="L7" rel="#L7">7</span>
<span id="L8" rel="#L8">8</span>
<span id="L9" rel="#L9">9</span>
<span id="L10" rel="#L10">10</span>
<span id="L11" rel="#L11">11</span>
<span id="L12" rel="#L12">12</span>
<span id="L13" rel="#L13">13</span>
<span id="L14" rel="#L14">14</span>
<span id="L15" rel="#L15">15</span>
<span id="L16" rel="#L16">16</span>
<span id="L17" rel="#L17">17</span>
<span id="L18" rel="#L18">18</span>
<span id="L19" rel="#L19">19</span>
<span id="L20" rel="#L20">20</span>
<span id="L21" rel="#L21">21</span>
<span id="L22" rel="#L22">22</span>
<span id="L23" rel="#L23">23</span>
<span id="L24" rel="#L24">24</span>
<span id="L25" rel="#L25">25</span>
<span id="L26" rel="#L26">26</span>
<span id="L27" rel="#L27">27</span>
<span id="L28" rel="#L28">28</span>
<span id="L29" rel="#L29">29</span>
<span id="L30" rel="#L30">30</span>
<span id="L31" rel="#L31">31</span>
<span id="L32" rel="#L32">32</span>
<span id="L33" rel="#L33">33</span>
<span id="L34" rel="#L34">34</span>
<span id="L35" rel="#L35">35</span>
<span id="L36" rel="#L36">36</span>
<span id="L37" rel="#L37">37</span>
<span id="L38" rel="#L38">38</span>
<span id="L39" rel="#L39">39</span>
<span id="L40" rel="#L40">40</span>
<span id="L41" rel="#L41">41</span>
<span id="L42" rel="#L42">42</span>
<span id="L43" rel="#L43">43</span>
<span id="L44" rel="#L44">44</span>
<span id="L45" rel="#L45">45</span>
<span id="L46" rel="#L46">46</span>
<span id="L47" rel="#L47">47</span>
<span id="L48" rel="#L48">48</span>
<span id="L49" rel="#L49">49</span>
<span id="L50" rel="#L50">50</span>
<span id="L51" rel="#L51">51</span>
<span id="L52" rel="#L52">52</span>
<span id="L53" rel="#L53">53</span>
<span id="L54" rel="#L54">54</span>
<span id="L55" rel="#L55">55</span>
<span id="L56" rel="#L56">56</span>
<span id="L57" rel="#L57">57</span>
<span id="L58" rel="#L58">58</span>
<span id="L59" rel="#L59">59</span>
<span id="L60" rel="#L60">60</span>
<span id="L61" rel="#L61">61</span>
<span id="L62" rel="#L62">62</span>
<span id="L63" rel="#L63">63</span>
<span id="L64" rel="#L64">64</span>
<span id="L65" rel="#L65">65</span>
<span id="L66" rel="#L66">66</span>
<span id="L67" rel="#L67">67</span>
<span id="L68" rel="#L68">68</span>
<span id="L69" rel="#L69">69</span>
<span id="L70" rel="#L70">70</span>
<span id="L71" rel="#L71">71</span>
<span id="L72" rel="#L72">72</span>
<span id="L73" rel="#L73">73</span>
<span id="L74" rel="#L74">74</span>
<span id="L75" rel="#L75">75</span>
<span id="L76" rel="#L76">76</span>
<span id="L77" rel="#L77">77</span>
<span id="L78" rel="#L78">78</span>
<span id="L79" rel="#L79">79</span>
<span id="L80" rel="#L80">80</span>
<span id="L81" rel="#L81">81</span>
<span id="L82" rel="#L82">82</span>
<span id="L83" rel="#L83">83</span>
<span id="L84" rel="#L84">84</span>
<span id="L85" rel="#L85">85</span>
<span id="L86" rel="#L86">86</span>
<span id="L87" rel="#L87">87</span>
<span id="L88" rel="#L88">88</span>
<span id="L89" rel="#L89">89</span>
<span id="L90" rel="#L90">90</span>
<span id="L91" rel="#L91">91</span>
<span id="L92" rel="#L92">92</span>
<span id="L93" rel="#L93">93</span>
<span id="L94" rel="#L94">94</span>
<span id="L95" rel="#L95">95</span>
<span id="L96" rel="#L96">96</span>
<span id="L97" rel="#L97">97</span>
<span id="L98" rel="#L98">98</span>
<span id="L99" rel="#L99">99</span>
<span id="L100" rel="#L100">100</span>
<span id="L101" rel="#L101">101</span>
<span id="L102" rel="#L102">102</span>
<span id="L103" rel="#L103">103</span>
<span id="L104" rel="#L104">104</span>
<span id="L105" rel="#L105">105</span>
<span id="L106" rel="#L106">106</span>
<span id="L107" rel="#L107">107</span>
<span id="L108" rel="#L108">108</span>
<span id="L109" rel="#L109">109</span>
<span id="L110" rel="#L110">110</span>
<span id="L111" rel="#L111">111</span>
<span id="L112" rel="#L112">112</span>
<span id="L113" rel="#L113">113</span>
<span id="L114" rel="#L114">114</span>
<span id="L115" rel="#L115">115</span>
<span id="L116" rel="#L116">116</span>
<span id="L117" rel="#L117">117</span>
<span id="L118" rel="#L118">118</span>
<span id="L119" rel="#L119">119</span>
<span id="L120" rel="#L120">120</span>
<span id="L121" rel="#L121">121</span>
<span id="L122" rel="#L122">122</span>
<span id="L123" rel="#L123">123</span>
<span id="L124" rel="#L124">124</span>
<span id="L125" rel="#L125">125</span>
<span id="L126" rel="#L126">126</span>
<span id="L127" rel="#L127">127</span>
<span id="L128" rel="#L128">128</span>
<span id="L129" rel="#L129">129</span>
<span id="L130" rel="#L130">130</span>
<span id="L131" rel="#L131">131</span>
<span id="L132" rel="#L132">132</span>
<span id="L133" rel="#L133">133</span>
<span id="L134" rel="#L134">134</span>
<span id="L135" rel="#L135">135</span>
<span id="L136" rel="#L136">136</span>
<span id="L137" rel="#L137">137</span>
<span id="L138" rel="#L138">138</span>
<span id="L139" rel="#L139">139</span>
<span id="L140" rel="#L140">140</span>
<span id="L141" rel="#L141">141</span>
<span id="L142" rel="#L142">142</span>
<span id="L143" rel="#L143">143</span>
<span id="L144" rel="#L144">144</span>
<span id="L145" rel="#L145">145</span>
<span id="L146" rel="#L146">146</span>
<span id="L147" rel="#L147">147</span>
<span id="L148" rel="#L148">148</span>
<span id="L149" rel="#L149">149</span>
<span id="L150" rel="#L150">150</span>
<span id="L151" rel="#L151">151</span>
<span id="L152" rel="#L152">152</span>
<span id="L153" rel="#L153">153</span>
<span id="L154" rel="#L154">154</span>
<span id="L155" rel="#L155">155</span>
<span id="L156" rel="#L156">156</span>
<span id="L157" rel="#L157">157</span>
<span id="L158" rel="#L158">158</span>
<span id="L159" rel="#L159">159</span>
<span id="L160" rel="#L160">160</span>
<span id="L161" rel="#L161">161</span>
<span id="L162" rel="#L162">162</span>
<span id="L163" rel="#L163">163</span>
<span id="L164" rel="#L164">164</span>
<span id="L165" rel="#L165">165</span>
<span id="L166" rel="#L166">166</span>
<span id="L167" rel="#L167">167</span>
<span id="L168" rel="#L168">168</span>
<span id="L169" rel="#L169">169</span>
<span id="L170" rel="#L170">170</span>
<span id="L171" rel="#L171">171</span>
<span id="L172" rel="#L172">172</span>
<span id="L173" rel="#L173">173</span>
<span id="L174" rel="#L174">174</span>
<span id="L175" rel="#L175">175</span>
<span id="L176" rel="#L176">176</span>
<span id="L177" rel="#L177">177</span>
<span id="L178" rel="#L178">178</span>
<span id="L179" rel="#L179">179</span>
<span id="L180" rel="#L180">180</span>
<span id="L181" rel="#L181">181</span>
<span id="L182" rel="#L182">182</span>
<span id="L183" rel="#L183">183</span>
<span id="L184" rel="#L184">184</span>
<span id="L185" rel="#L185">185</span>
<span id="L186" rel="#L186">186</span>
<span id="L187" rel="#L187">187</span>
<span id="L188" rel="#L188">188</span>
<span id="L189" rel="#L189">189</span>
<span id="L190" rel="#L190">190</span>
<span id="L191" rel="#L191">191</span>
<span id="L192" rel="#L192">192</span>
<span id="L193" rel="#L193">193</span>
<span id="L194" rel="#L194">194</span>
<span id="L195" rel="#L195">195</span>
<span id="L196" rel="#L196">196</span>
<span id="L197" rel="#L197">197</span>
<span id="L198" rel="#L198">198</span>
<span id="L199" rel="#L199">199</span>
<span id="L200" rel="#L200">200</span>
<span id="L201" rel="#L201">201</span>
<span id="L202" rel="#L202">202</span>
<span id="L203" rel="#L203">203</span>
<span id="L204" rel="#L204">204</span>
<span id="L205" rel="#L205">205</span>
<span id="L206" rel="#L206">206</span>
<span id="L207" rel="#L207">207</span>
<span id="L208" rel="#L208">208</span>
<span id="L209" rel="#L209">209</span>
<span id="L210" rel="#L210">210</span>
<span id="L211" rel="#L211">211</span>
<span id="L212" rel="#L212">212</span>
<span id="L213" rel="#L213">213</span>
<span id="L214" rel="#L214">214</span>
<span id="L215" rel="#L215">215</span>
<span id="L216" rel="#L216">216</span>
<span id="L217" rel="#L217">217</span>
<span id="L218" rel="#L218">218</span>
<span id="L219" rel="#L219">219</span>
<span id="L220" rel="#L220">220</span>
<span id="L221" rel="#L221">221</span>
<span id="L222" rel="#L222">222</span>
<span id="L223" rel="#L223">223</span>
<span id="L224" rel="#L224">224</span>
<span id="L225" rel="#L225">225</span>
<span id="L226" rel="#L226">226</span>
<span id="L227" rel="#L227">227</span>
<span id="L228" rel="#L228">228</span>
<span id="L229" rel="#L229">229</span>
<span id="L230" rel="#L230">230</span>
<span id="L231" rel="#L231">231</span>
<span id="L232" rel="#L232">232</span>
<span id="L233" rel="#L233">233</span>
<span id="L234" rel="#L234">234</span>
<span id="L235" rel="#L235">235</span>
<span id="L236" rel="#L236">236</span>
<span id="L237" rel="#L237">237</span>
<span id="L238" rel="#L238">238</span>
<span id="L239" rel="#L239">239</span>
<span id="L240" rel="#L240">240</span>
<span id="L241" rel="#L241">241</span>
<span id="L242" rel="#L242">242</span>
<span id="L243" rel="#L243">243</span>
<span id="L244" rel="#L244">244</span>
<span id="L245" rel="#L245">245</span>
<span id="L246" rel="#L246">246</span>
<span id="L247" rel="#L247">247</span>
<span id="L248" rel="#L248">248</span>
<span id="L249" rel="#L249">249</span>
<span id="L250" rel="#L250">250</span>
<span id="L251" rel="#L251">251</span>
<span id="L252" rel="#L252">252</span>
<span id="L253" rel="#L253">253</span>
<span id="L254" rel="#L254">254</span>
<span id="L255" rel="#L255">255</span>
<span id="L256" rel="#L256">256</span>
<span id="L257" rel="#L257">257</span>
<span id="L258" rel="#L258">258</span>
<span id="L259" rel="#L259">259</span>
<span id="L260" rel="#L260">260</span>
<span id="L261" rel="#L261">261</span>
<span id="L262" rel="#L262">262</span>
<span id="L263" rel="#L263">263</span>
<span id="L264" rel="#L264">264</span>
<span id="L265" rel="#L265">265</span>
<span id="L266" rel="#L266">266</span>
<span id="L267" rel="#L267">267</span>
<span id="L268" rel="#L268">268</span>
<span id="L269" rel="#L269">269</span>
<span id="L270" rel="#L270">270</span>
<span id="L271" rel="#L271">271</span>
<span id="L272" rel="#L272">272</span>
<span id="L273" rel="#L273">273</span>
<span id="L274" rel="#L274">274</span>
<span id="L275" rel="#L275">275</span>
<span id="L276" rel="#L276">276</span>
<span id="L277" rel="#L277">277</span>
<span id="L278" rel="#L278">278</span>
<span id="L279" rel="#L279">279</span>
<span id="L280" rel="#L280">280</span>
<span id="L281" rel="#L281">281</span>
<span id="L282" rel="#L282">282</span>
<span id="L283" rel="#L283">283</span>
<span id="L284" rel="#L284">284</span>
<span id="L285" rel="#L285">285</span>
<span id="L286" rel="#L286">286</span>
<span id="L287" rel="#L287">287</span>
<span id="L288" rel="#L288">288</span>
<span id="L289" rel="#L289">289</span>
<span id="L290" rel="#L290">290</span>
<span id="L291" rel="#L291">291</span>
<span id="L292" rel="#L292">292</span>
<span id="L293" rel="#L293">293</span>
<span id="L294" rel="#L294">294</span>
<span id="L295" rel="#L295">295</span>
<span id="L296" rel="#L296">296</span>
<span id="L297" rel="#L297">297</span>
<span id="L298" rel="#L298">298</span>
<span id="L299" rel="#L299">299</span>
<span id="L300" rel="#L300">300</span>
<span id="L301" rel="#L301">301</span>
<span id="L302" rel="#L302">302</span>
<span id="L303" rel="#L303">303</span>
<span id="L304" rel="#L304">304</span>
<span id="L305" rel="#L305">305</span>
<span id="L306" rel="#L306">306</span>
<span id="L307" rel="#L307">307</span>
<span id="L308" rel="#L308">308</span>
<span id="L309" rel="#L309">309</span>
<span id="L310" rel="#L310">310</span>
<span id="L311" rel="#L311">311</span>
<span id="L312" rel="#L312">312</span>
<span id="L313" rel="#L313">313</span>
<span id="L314" rel="#L314">314</span>
<span id="L315" rel="#L315">315</span>
<span id="L316" rel="#L316">316</span>
<span id="L317" rel="#L317">317</span>
<span id="L318" rel="#L318">318</span>
<span id="L319" rel="#L319">319</span>
<span id="L320" rel="#L320">320</span>
<span id="L321" rel="#L321">321</span>
<span id="L322" rel="#L322">322</span>
<span id="L323" rel="#L323">323</span>
<span id="L324" rel="#L324">324</span>
<span id="L325" rel="#L325">325</span>
<span id="L326" rel="#L326">326</span>
<span id="L327" rel="#L327">327</span>
<span id="L328" rel="#L328">328</span>
<span id="L329" rel="#L329">329</span>
<span id="L330" rel="#L330">330</span>
<span id="L331" rel="#L331">331</span>
<span id="L332" rel="#L332">332</span>
<span id="L333" rel="#L333">333</span>
<span id="L334" rel="#L334">334</span>
<span id="L335" rel="#L335">335</span>
<span id="L336" rel="#L336">336</span>
<span id="L337" rel="#L337">337</span>
<span id="L338" rel="#L338">338</span>
<span id="L339" rel="#L339">339</span>
<span id="L340" rel="#L340">340</span>
<span id="L341" rel="#L341">341</span>
<span id="L342" rel="#L342">342</span>
<span id="L343" rel="#L343">343</span>
<span id="L344" rel="#L344">344</span>
<span id="L345" rel="#L345">345</span>
<span id="L346" rel="#L346">346</span>
<span id="L347" rel="#L347">347</span>
<span id="L348" rel="#L348">348</span>
<span id="L349" rel="#L349">349</span>
<span id="L350" rel="#L350">350</span>
<span id="L351" rel="#L351">351</span>
<span id="L352" rel="#L352">352</span>
<span id="L353" rel="#L353">353</span>
<span id="L354" rel="#L354">354</span>
<span id="L355" rel="#L355">355</span>
<span id="L356" rel="#L356">356</span>
<span id="L357" rel="#L357">357</span>
<span id="L358" rel="#L358">358</span>
<span id="L359" rel="#L359">359</span>
<span id="L360" rel="#L360">360</span>
<span id="L361" rel="#L361">361</span>
<span id="L362" rel="#L362">362</span>
<span id="L363" rel="#L363">363</span>
<span id="L364" rel="#L364">364</span>
<span id="L365" rel="#L365">365</span>
<span id="L366" rel="#L366">366</span>
<span id="L367" rel="#L367">367</span>
<span id="L368" rel="#L368">368</span>
</pre>
          </td>
          <td width="100%">
                  <div class="highlight"><pre><div class='line' id='LC1'><span class="c">#!/bin/bash</span></div><div class='line' id='LC2'><br/></div><div class='line' id='LC3'><span class="c">#  OwncloudPie - Shell script for installing and updating Owncloud on the Raspberry Pi.</span></div><div class='line' id='LC4'><span class="c"># </span></div><div class='line' id='LC5'><span class="c">#  (c) Copyright 2012  Florian Müller (petrockblock@gmail.com)</span></div><div class='line' id='LC6'><span class="c"># </span></div><div class='line' id='LC7'><span class="c">#  OwncloudPie homepage: https://github.com/petrockblog/OwncloudPie</span></div><div class='line' id='LC8'><span class="c"># </span></div><div class='line' id='LC9'><span class="c">#  Permission to use, copy, modify and distribute OwncloudPie in both binary and</span></div><div class='line' id='LC10'><span class="c">#  source form, for non-commercial purposes, is hereby granted without fee,</span></div><div class='line' id='LC11'><span class="c">#  providing that this license information and copyright notice appear with</span></div><div class='line' id='LC12'><span class="c">#  all copies and any derived work.</span></div><div class='line' id='LC13'><span class="c"># </span></div><div class='line' id='LC14'><span class="c">#  This software is provided &#39;as-is&#39;, without any express or implied</span></div><div class='line' id='LC15'><span class="c">#  warranty. In no event shall the authors be held liable for any damages</span></div><div class='line' id='LC16'><span class="c">#  arising from the use of this software.</span></div><div class='line' id='LC17'><span class="c"># </span></div><div class='line' id='LC18'><span class="c">#  OwncloudPie is freeware for PERSONAL USE only. Commercial users should</span></div><div class='line' id='LC19'><span class="c">#  seek permission of the copyright holders first. Commercial use includes</span></div><div class='line' id='LC20'><span class="c">#  charging money for OwncloudPie or software derived from OwncloudPie.</span></div><div class='line' id='LC21'><span class="c"># </span></div><div class='line' id='LC22'><span class="c">#  The copyright holders request that bug fixes and improvements to the code</span></div><div class='line' id='LC23'><span class="c">#  should be forwarded to them so everyone can benefit from the modifications</span></div><div class='line' id='LC24'><span class="c">#  in future versions.</span></div><div class='line' id='LC25'><span class="c"># </span></div><div class='line' id='LC26'><span class="c">#  Raspberry Pi is a trademark of the Raspberry Pi Foundation.</span></div><div class='line' id='LC27'><span class="c"># </span></div><div class='line' id='LC28'><br/></div><div class='line' id='LC29'><span class="k">function </span>printMsg<span class="o">()</span></div><div class='line' id='LC30'><span class="o">{</span></div><div class='line' id='LC31'>	<span class="nb">echo</span> -e <span class="s2">&quot;\n= = = = = = = = = = = = = = = = = = = = =\n$1\n= = = = = = = = = = = = = = = = = = = = =\n&quot;</span></div><div class='line' id='LC32'><span class="o">}</span></div><div class='line' id='LC33'><br/></div><div class='line' id='LC34'><span class="c"># arg 1: key, arg 2: value, arg 3: file</span></div><div class='line' id='LC35'><span class="k">function </span>ensureKeyValue<span class="o">()</span></div><div class='line' id='LC36'><span class="o">{</span></div><div class='line' id='LC37'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">if</span> <span class="o">[[</span> -z <span class="k">$(</span>egrep -i <span class="s2">&quot;;? *$1 = [0-9]*[M]?&quot;</span> <span class="nv">$3</span><span class="k">)</span> <span class="o">]]</span>; <span class="k">then</span></div><div class='line' id='LC38'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c"># add key-value pair</span></div><div class='line' id='LC39'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="nb">echo</span> <span class="s2">&quot;$1 = $2&quot;</span> &gt;&gt; <span class="nv">$3</span></div><div class='line' id='LC40'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">else</span></div><div class='line' id='LC41'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c"># replace existing key-value pair</span></div><div class='line' id='LC42'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="nv">toreplace</span><span class="o">=</span><span class="sb">`</span>egrep -i <span class="s2">&quot;;? *$1 = [0-9]*[M]?&quot;</span> <span class="nv">$3</span><span class="sb">`</span></div><div class='line' id='LC43'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sed <span class="nv">$3</span> -i -e <span class="s2">&quot;s|$toreplace|$1 = $2|g&quot;</span></div><div class='line' id='LC44'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">fi</span>     </div><div class='line' id='LC45'><span class="o">}</span></div><div class='line' id='LC46'><br/></div><div class='line' id='LC47'><span class="c"># arg 1: key, arg 2: value, arg 3: file</span></div><div class='line' id='LC48'><span class="c"># make sure that a key-value pair is set in file</span></div><div class='line' id='LC49'><span class="c"># key=value</span></div><div class='line' id='LC50'><span class="k">function </span>ensureKeyValueShort<span class="o">()</span></div><div class='line' id='LC51'><span class="o">{</span></div><div class='line' id='LC52'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">if</span> <span class="o">[[</span> -z <span class="k">$(</span>egrep -i <span class="s2">&quot;#? *$1\s?=\s?&quot;&quot;?[+|-]?[0-9]*[a-z]*&quot;&quot;&quot;</span>? <span class="nv">$3</span><span class="k">)</span> <span class="o">]]</span>; <span class="k">then</span></div><div class='line' id='LC53'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c"># add key-value pair</span></div><div class='line' id='LC54'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="nb">echo</span> <span class="s2">&quot;$1=&quot;&quot;$2&quot;&quot;&quot;</span> &gt;&gt; <span class="nv">$3</span></div><div class='line' id='LC55'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">else</span></div><div class='line' id='LC56'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c"># replace existing key-value pair</span></div><div class='line' id='LC57'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="nv">toreplace</span><span class="o">=</span><span class="sb">`</span>egrep -i <span class="s2">&quot;#? *$1\s?=\s?&quot;&quot;?[+|-]?[0-9]*[a-z]*&quot;&quot;&quot;</span>? <span class="nv">$3</span><span class="sb">`</span></div><div class='line' id='LC58'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sed <span class="nv">$3</span> -i -e <span class="s2">&quot;s|$toreplace|$1=&quot;&quot;$2&quot;&quot;|g&quot;</span></div><div class='line' id='LC59'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">fi</span>     </div><div class='line' id='LC60'><span class="o">}</span></div><div class='line' id='LC61'><br/></div><div class='line' id='LC62'><span class="k">function </span>checkNeededPackages<span class="o">()</span></div><div class='line' id='LC63'><span class="o">{</span></div><div class='line' id='LC64'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="nv">doexit</span><span class="o">=</span>0</div><div class='line' id='LC65'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="nb">type</span> -P git &amp;&gt;/dev/null <span class="o">&amp;&amp;</span> <span class="nb">echo</span> <span class="s2">&quot;Found git command.&quot;</span> <span class="o">||</span> <span class="o">{</span> <span class="nb">echo</span> <span class="s2">&quot;Did not find git. Try &#39;sudo apt-get install -y git&#39; first.&quot;</span>; <span class="nv">doexit</span><span class="o">=</span>1; <span class="o">}</span></div><div class='line' id='LC66'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="nb">type</span> -P dialog &amp;&gt;/dev/null <span class="o">&amp;&amp;</span> <span class="nb">echo</span> <span class="s2">&quot;Found dialog command.&quot;</span> <span class="o">||</span> <span class="o">{</span> <span class="nb">echo</span> <span class="s2">&quot;Did not find dialog. Try &#39;sudo apt-get install -y dialog&#39; first.&quot;</span>; <span class="nv">doexit</span><span class="o">=</span>1; <span class="o">}</span></div><div class='line' id='LC67'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">if</span> <span class="o">[[</span> doexit -eq 1 <span class="o">]]</span>; <span class="k">then</span></div><div class='line' id='LC68'><span class="k">        </span><span class="nb">exit </span>1</div><div class='line' id='LC69'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">fi</span></div><div class='line' id='LC70'><span class="o">}</span></div><div class='line' id='LC71'><br/></div><div class='line' id='LC72'><span class="k">function </span>downloadLatestOwncloudRelease<span class="o">()</span></div><div class='line' id='LC73'><span class="o">{</span></div><div class='line' id='LC74'>	clear</div><div class='line' id='LC75'><br/></div><div class='line' id='LC76'>	<span class="k">if</span> <span class="o">[[</span> ! -d /var/www/owncloud <span class="o">]]</span>; <span class="k">then</span></div><div class='line' id='LC77'><span class="k">		</span><span class="nb">echo</span> <span class="s2">&quot;Cannot find directory /var/www/owncloud. &quot;</span></div><div class='line' id='LC78'>		<span class="nb">exit </span>1</div><div class='line' id='LC79'>	<span class="k">fi</span></div><div class='line' id='LC80'><br/></div><div class='line' id='LC81'><span class="k">	</span>printMsg <span class="s2">&quot;Updating to latest Owncloud release.&quot;</span></div><div class='line' id='LC82'><br/></div><div class='line' id='LC83'>	<span class="c"># download and extract the latest release of Owncloud (4.5.2 at this time)</span></div><div class='line' id='LC84'>	wget http://owncloud.org/releases/Changelog</div><div class='line' id='LC85'>	<span class="nv">latestrelease</span><span class="o">=</span><span class="k">$(</span>cat Changelog | grep Download | head -n 1<span class="k">)</span></div><div class='line' id='LC86'>	<span class="nv">latestrelease</span><span class="o">=</span><span class="k">${</span><span class="nv">latestrelease</span><span class="p">:</span><span class="nv">10</span><span class="k">}</span></div><div class='line' id='LC87'>	wget <span class="s2">&quot;$latestrelease&quot;</span></div><div class='line' id='LC88'>	tar -xjf <span class="s2">&quot;$(basename $latestrelease)&quot;</span></div><div class='line' id='LC89'>	rm <span class="s2">&quot;$(basename $latestrelease)&quot;</span></div><div class='line' id='LC90'>	rm Changelog</div><div class='line' id='LC91'><span class="o">}</span></div><div class='line' id='LC92'><br/></div><div class='line' id='LC93'><span class="k">function </span>writeServerConfig<span class="o">()</span></div><div class='line' id='LC94'><span class="o">{</span></div><div class='line' id='LC95'>	cat &gt; /etc/nginx/sites-available/default <span class="s">&lt;&lt; _EOF_</span></div><div class='line' id='LC96'><span class="s"># owncloud</span></div><div class='line' id='LC97'><span class="s">server {</span></div><div class='line' id='LC98'><span class="s">  listen 80;</span></div><div class='line' id='LC99'><span class="s">    server_name $__servername;</span></div><div class='line' id='LC100'><span class="s">    rewrite ^ https://\$server_name\$request_uri? permanent;  # enforce https</span></div><div class='line' id='LC101'><span class="s">}</span></div><div class='line' id='LC102'><br/></div><div class='line' id='LC103'><span class="s"># owncloud (ssl/tls)</span></div><div class='line' id='LC104'><span class="s">server {</span></div><div class='line' id='LC105'><span class="s">  listen 443 ssl;</span></div><div class='line' id='LC106'><span class="s">  server_name $__servername;</span></div><div class='line' id='LC107'><span class="s">  ssl_certificate /etc/nginx/cert.pem;</span></div><div class='line' id='LC108'><span class="s">  ssl_certificate_key /etc/nginx/cert.key;</span></div><div class='line' id='LC109'><span class="s">  root /var/www;</span></div><div class='line' id='LC110'><span class="s">  index index.php;</span></div><div class='line' id='LC111'><span class="s">  client_max_body_size 1000M; # set maximum upload size</span></div><div class='line' id='LC112'><span class="s">  fastcgi_buffers 64 4K;</span></div><div class='line' id='LC113'><br/></div><div class='line' id='LC114'><span class="s">  # deny direct access</span></div><div class='line' id='LC115'><span class="s">  location ~ ^/owncloud/(data|config|\.ht|db_structure\.xml|README) {</span></div><div class='line' id='LC116'><span class="s">    deny all;</span></div><div class='line' id='LC117'><span class="s">  }</span></div><div class='line' id='LC118'><br/></div><div class='line' id='LC119'><span class="s">  # default try order</span></div><div class='line' id='LC120'><span class="s">  location / {</span></div><div class='line' id='LC121'><span class="s">    try_files \$uri \$uri/ index.php;</span></div><div class='line' id='LC122'><span class="s">  }</span></div><div class='line' id='LC123'><br/></div><div class='line' id='LC124'><span class="s">  # owncloud WebDAV</span></div><div class='line' id='LC125'><span class="s">  location @webdav {</span></div><div class='line' id='LC126'><span class="s">    fastcgi_split_path_info ^(.+\.php)(/.*)$;</span></div><div class='line' id='LC127'><span class="s">    fastcgi_pass 127.0.0.1:9000;</span></div><div class='line' id='LC128'><span class="s">    fastcgi_param SCRIPT_FILENAME \$document_root\$fastcgi_script_name;</span></div><div class='line' id='LC129'><span class="s">    fastcgi_param HTTPS on;</span></div><div class='line' id='LC130'><span class="s">    include fastcgi_params;</span></div><div class='line' id='LC131'><span class="s">  }</span></div><div class='line' id='LC132'><br/></div><div class='line' id='LC133'><span class="s">  # enable php</span></div><div class='line' id='LC134'><span class="s">  location ~ ^(?&lt;script_name&gt;.+?\.php)(?&lt;path_info&gt;/.*)?$ {</span></div><div class='line' id='LC135'><span class="s">    try_files \$script_name = 404;</span></div><div class='line' id='LC136'><span class="s">    include fastcgi_params;</span></div><div class='line' id='LC137'><span class="s">    fastcgi_param PATH_INFO \$path_info;</span></div><div class='line' id='LC138'><span class="s">    fastcgi_param HTTPS on;</span></div><div class='line' id='LC139'><span class="s">    fastcgi_pass 127.0.0.1:9000;</span></div><div class='line' id='LC140'><span class="s">  }</span></div><div class='line' id='LC141'><span class="s">}    </span></div><div class='line' id='LC142'><span class="s">_EOF_</span></div><div class='line' id='LC143'><span class="o">}</span></div><div class='line' id='LC144'><br/></div><div class='line' id='LC145'><span class="k">function </span>main_setservername<span class="o">()</span></div><div class='line' id='LC146'><span class="o">{</span></div><div class='line' id='LC147'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="nv">cmd</span><span class="o">=(</span>dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --inputbox <span class="s2">&quot;Please enter the URL of your Owncloud server.&quot;</span> 22 76 16<span class="o">)</span></div><div class='line' id='LC148'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="nv">choices</span><span class="o">=</span><span class="k">$(</span><span class="s2">&quot;${cmd[@]}&quot;</span> 2&gt;&amp;1 &gt;/dev/tty<span class="k">)</span>    </div><div class='line' id='LC149'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">if</span> <span class="o">[</span> <span class="s2">&quot;$choices&quot;</span> !<span class="o">=</span> <span class="s2">&quot;&quot;</span> <span class="o">]</span>; <span class="k">then</span></div><div class='line' id='LC150'><span class="k">        </span><span class="nv">__servername</span><span class="o">=</span><span class="nv">$choices</span></div><div class='line' id='LC151'><br/></div><div class='line' id='LC152'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">if</span> <span class="o">[[</span> -f /etc/nginx/sites-available/default <span class="o">]]</span>; <span class="k">then</span></div><div class='line' id='LC153'><span class="k">          </span>sed /etc/nginx/sites-available/default -i -r -e <span class="s2">&quot;s|server_name [a-zA-Z.]+|server_name $__servername|g&quot;</span></div><div class='line' id='LC154'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">fi</span></div><div class='line' id='LC155'><br/></div><div class='line' id='LC156'><span class="k">    else</span></div><div class='line' id='LC157'><span class="k">        </span><span class="nb">break</span></div><div class='line' id='LC158'><span class="nb">    </span><span class="k">fi</span>  </div><div class='line' id='LC159'><span class="o">}</span></div><div class='line' id='LC160'><br/></div><div class='line' id='LC161'><span class="k">function </span>main_newinstall_nginx<span class="o">()</span></div><div class='line' id='LC162'><span class="o">{</span></div><div class='line' id='LC163'>	clear </div><div class='line' id='LC164'><br/></div><div class='line' id='LC165'>	<span class="c"># make sure we use the newest packages</span></div><div class='line' id='LC166'>	apt-get update</div><div class='line' id='LC167'>	apt-get upgrade -y</div><div class='line' id='LC168'><br/></div><div class='line' id='LC169'>	<span class="c"># make sure that the group www-data exists</span></div><div class='line' id='LC170'>	groupadd www-data</div><div class='line' id='LC171'>	usermod -a -G www-data www-data</div><div class='line' id='LC172'><br/></div><div class='line' id='LC173'>	<span class="c"># install all needed packages, e.g., Apache, PHP, SQLite</span></div><div class='line' id='LC174'>&nbsp;&nbsp;apt-get remove -y apache2</div><div class='line' id='LC175'>&nbsp;&nbsp;apt-get install -y nginx sendmail sendmail-bin openssl ssl-cert php5-cli php5-sqlite php5-gd php5-curl php5-common php5-cgi sqlite php-pear php-apc git-core</div><div class='line' id='LC176'>&nbsp;&nbsp;apt-get install -y autoconf automake autotools-dev curl libapr1 libtool curl libcurl4-openssl-dev php-xml-parser php5 php5-dev php5-gd php5-fpm</div><div class='line' id='LC177'>&nbsp;&nbsp;apt-get install -y memcached php5-memcache varnish dphys-swapfile</div><div class='line' id='LC178'>&nbsp;&nbsp;apt-get autoremove -y</div><div class='line' id='LC179'><br/></div><div class='line' id='LC180'>	<span class="c"># set memory split to 240 MB RAM and 16 MB video</span></div><div class='line' id='LC181'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;gpu_mem&quot;</span> <span class="s2">&quot;16&quot;</span> <span class="s2">&quot;/boot/config.txt&quot;</span></div><div class='line' id='LC182'><br/></div><div class='line' id='LC183'>	<span class="c"># generate self-signed certificate that is valid for one year</span></div><div class='line' id='LC184'>&nbsp;&nbsp;dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --msgbox <span class="s2">&quot;We are now going to create a self-signed certificate. While you could simply press ENTER when you are asked for country name etc. or enter whatever you want, it might be beneficial to have the web servers host name in the common name field of the certificate.&quot;</span> 20 60    </div><div class='line' id='LC185'><br/></div><div class='line' id='LC186'>	openssl req <span class="nv">$@</span> -new -x509 -days 365 -nodes -out /etc/nginx/cert.pem -keyout /etc/nginx/cert.key</div><div class='line' id='LC187'>	chmod 600 /etc/nginx/cert.pem</div><div class='line' id='LC188'>	chmod 600 /etc/nginx/cert.key</div><div class='line' id='LC189'><br/></div><div class='line' id='LC190'>	writeServerConfig</div><div class='line' id='LC191'>	sed /etc/php5/fpm/pool.d/www.conf -i -e <span class="s2">&quot;s|listen = /var/run/php5-fpm.sock|listen = 127.0.0.1:9000|g&quot;</span></div><div class='line' id='LC192'><br/></div><div class='line' id='LC193'>&nbsp;&nbsp;ensureKeyValue <span class="s2">&quot;upload_max_filesize&quot;</span> <span class="s2">&quot;1000M&quot;</span> <span class="s2">&quot;/etc/php5/fpm/php.ini&quot;</span></div><div class='line' id='LC194'>&nbsp;&nbsp;ensureKeyValue <span class="s2">&quot;post_max_size&quot;</span> <span class="s2">&quot;1000M&quot;</span> <span class="s2">&quot;/etc/php5/fpm/php.ini&quot;</span></div><div class='line' id='LC195'><br/></div><div class='line' id='LC196'>&nbsp;&nbsp;ensureKeyValue <span class="s2">&quot;upload_tmp_dir&quot;</span> <span class="s2">&quot;/srv/http/owncloud/data&quot;</span> <span class="s2">&quot;/etc/php5/fpm/php.ini&quot;</span></div><div class='line' id='LC197'>&nbsp;&nbsp;mkdir -p /srv/http/owncloud/data</div><div class='line' id='LC198'>&nbsp;&nbsp;chown www-data:www-data /srv/http/owncloud/data</div><div class='line' id='LC199'><br/></div><div class='line' id='LC200'>&nbsp;&nbsp;sed /etc/nginx/sites-available/default -i -e <span class="s2">&quot;s|client_max_body_size [0-9]*[M]?;|client_max_body_size 1000M;|g&quot;</span></div><div class='line' id='LC201'><br/></div><div class='line' id='LC202'>	/etc/init.d/php5-fpm restart</div><div class='line' id='LC203'>	/etc/init.d/nginx restart</div><div class='line' id='LC204'><br/></div><div class='line' id='LC205'>	<span class="c"># set ARM frequency to 800 MHz (or use the raspi-config tool to set clock speed)</span></div><div class='line' id='LC206'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;arm_freq&quot;</span> <span class="s2">&quot;800&quot;</span> <span class="s2">&quot;/boot/config.txt&quot;</span></div><div class='line' id='LC207'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;sdram_freq&quot;</span> <span class="s2">&quot;400&quot;</span> <span class="s2">&quot;/boot/config.txt&quot;</span></div><div class='line' id='LC208'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;core_freq&quot;</span> <span class="s2">&quot;250&quot;</span> <span class="s2">&quot;/boot/config.txt&quot;</span></div><div class='line' id='LC209'><br/></div><div class='line' id='LC210'>	<span class="c"># resize swap file to 512 MB</span></div><div class='line' id='LC211'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;CONF_SWAPSIZE&quot;</span> <span class="s2">&quot;512&quot;</span> <span class="s2">&quot;/etc/dphys-swapfile&quot;</span></div><div class='line' id='LC212'>	dphys-swapfile setup</div><div class='line' id='LC213'>	dphys-swapfile swapon</div><div class='line' id='LC214'><br/></div><div class='line' id='LC215'>&nbsp;&nbsp;mkdir -p /var/www/owncloud</div><div class='line' id='LC216'>&nbsp;&nbsp;downloadLatestOwncloudRelease</div><div class='line' id='LC217'>	mv owncloud/ /var/www/</div><div class='line' id='LC218'><br/></div><div class='line' id='LC219'>	<span class="c"># change group and owner of all /var/www files recursively to www-data</span></div><div class='line' id='LC220'>	chown -R www-data:www-data /var/www</div><div class='line' id='LC221'><br/></div><div class='line' id='LC222'>&nbsp;&nbsp;<span class="c"># enable US UTF-8 locale</span></div><div class='line' id='LC223'>&nbsp;&nbsp;sudo sed -i -e <span class="s2">&quot;s/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/g&quot;</span> /etc/locale.gen</div><div class='line' id='LC224'><br/></div><div class='line' id='LC225'>	<span class="c"># finish the script</span></div><div class='line' id='LC226'>	<span class="nv">myipaddress</span><span class="o">=</span><span class="k">$(</span>hostname -I | tr -d <span class="s1">&#39; &#39;</span><span class="k">)</span></div><div class='line' id='LC227'>&nbsp;&nbsp;dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --msgbox <span class="s2">&quot;If everything went right, Owncloud should now be available at the URL https://$myipaddress/owncloud. You have to finish the setup by visiting that site.&quot;</span> 20 60    </div><div class='line' id='LC228'><span class="o">}</span></div><div class='line' id='LC229'><br/></div><div class='line' id='LC230'><span class="k">function </span>main_newinstall_apache<span class="o">()</span></div><div class='line' id='LC231'><span class="o">{</span></div><div class='line' id='LC232'>&nbsp;&nbsp;clear </div><div class='line' id='LC233'><br/></div><div class='line' id='LC234'>&nbsp;&nbsp;<span class="c"># make sure we use the newest packages</span></div><div class='line' id='LC235'>&nbsp;&nbsp;apt-get update</div><div class='line' id='LC236'>&nbsp;&nbsp;apt-get upgrade -y</div><div class='line' id='LC237'><br/></div><div class='line' id='LC238'>&nbsp;&nbsp;<span class="c"># make sure that the group www-data exists</span></div><div class='line' id='LC239'>&nbsp;&nbsp;groupadd www-data</div><div class='line' id='LC240'>&nbsp;&nbsp;usermod -a -G www-data www-data</div><div class='line' id='LC241'><br/></div><div class='line' id='LC242'>&nbsp;&nbsp;<span class="c"># install all needed packages, e.g., Apache, PHP, SQLite</span></div><div class='line' id='LC243'>&nbsp;&nbsp;apt-get install -y apache2 openssl sendmail sendmail-bin ssl-cert libapache2-mod-php5 php5-cli php5-sqlite php5-gd php5-curl php5-common php5-cgi sqlite php-pear php-apc git-core ca-certificates dphys-swapfile</div><div class='line' id='LC244'><br/></div><div class='line' id='LC245'>&nbsp;&nbsp;<span class="c"># Change RAM settings 16 MB video RAM</span></div><div class='line' id='LC246'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;gpu_mem&quot;</span> <span class="s2">&quot;16&quot;</span> <span class="s2">&quot;/boot/config.txt&quot;</span></div><div class='line' id='LC247'><br/></div><div class='line' id='LC248'>&nbsp;&nbsp;<span class="c"># generate self-signed certificate that is valid for one year</span></div><div class='line' id='LC249'>&nbsp;&nbsp;dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --msgbox <span class="s2">&quot;We are now going to create a self-signed certificate. While you could simply press ENTER when you are asked for country name etc. or enter whatever you want, it might be beneficial to have the web servers host name in the common name field of the certificate.&quot;</span> 20 60    </div><div class='line' id='LC250'>&nbsp;&nbsp;clear</div><div class='line' id='LC251'>&nbsp;&nbsp;openssl req <span class="nv">$@</span> -new -x509 -days 365 -nodes -out /etc/apache2/apache.pem -keyout /etc/apache2/apache.pem</div><div class='line' id='LC252'>&nbsp;&nbsp;chmod 600 /etc/apache2/apache.pem</div><div class='line' id='LC253'><br/></div><div class='line' id='LC254'>&nbsp;&nbsp;<span class="c"># enable Apache modules (as explained at http://owncloud.org/support/install/, Section 2.3)</span></div><div class='line' id='LC255'>&nbsp;&nbsp;a2enmod ssl</div><div class='line' id='LC256'>&nbsp;&nbsp;a2enmod rewrite</div><div class='line' id='LC257'>&nbsp;&nbsp;a2enmod headers</div><div class='line' id='LC258'><br/></div><div class='line' id='LC259'>&nbsp;&nbsp;<span class="c"># disable unneccessary (for Owncloud) module(s)</span></div><div class='line' id='LC260'>&nbsp;&nbsp;a2dismod cgi</div><div class='line' id='LC261'>&nbsp;&nbsp;a2dismod authz_groupfile</div><div class='line' id='LC262'><br/></div><div class='line' id='LC263'>&nbsp;&nbsp;<span class="c"># configure Apache to use self-signed certificate</span></div><div class='line' id='LC264'>&nbsp;&nbsp;mv /etc/apache2/sites-available/default-ssl /etc/apache2/sites-available/default-ssl.bak</div><div class='line' id='LC265'>&nbsp;&nbsp;sed <span class="s1">&#39;s|/etc/ssl/certs/ssl-cert-snakeoil.pem|/etc/apache2/apache.pem|g;s|AllowOverride None|AllowOverride All|g;s|SSLCertificateKeyFile|# SSLCertificateKeyFile|g&#39;</span> /etc/apache2/sites-available/default-ssl.bak &gt; tmp</div><div class='line' id='LC266'>&nbsp;&nbsp;mv tmp /etc/apache2/sites-available/default-ssl</div><div class='line' id='LC267'><br/></div><div class='line' id='LC268'>&nbsp;&nbsp;<span class="c"># limit number of parallel Apache processes</span></div><div class='line' id='LC269'>&nbsp;&nbsp;mv /etc/apache2/apache2.conf /etc/apache2/apache2.conf.bak </div><div class='line' id='LC270'>&nbsp;&nbsp;sed <span class="s1">&#39;s|StartServers          5|StartServers          2|g;s|MinSpareServers       5|MinSpareServers       2|g;s|MaxSpareServers      10|MaxSpareServers       3|g&#39;</span> /etc/apache2/apache2.conf.bak &gt; tmp</div><div class='line' id='LC271'>&nbsp;&nbsp;mv tmp /etc/apache2/apache2.conf</div><div class='line' id='LC272'><br/></div><div class='line' id='LC273'>&nbsp;&nbsp;<span class="c"># set ARM frequency to 800 MHz (attention: should be safe, but do this at your own risk)</span></div><div class='line' id='LC274'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;arm_freq&quot;</span> <span class="s2">&quot;800&quot;</span> <span class="s2">&quot;/boot/config.txt&quot;</span></div><div class='line' id='LC275'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;sdram_freq&quot;</span> <span class="s2">&quot;450&quot;</span> <span class="s2">&quot;/boot/config.txt&quot;</span></div><div class='line' id='LC276'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;core_freq&quot;</span> <span class="s2">&quot;350&quot;</span> <span class="s2">&quot;/boot/config.txt&quot;</span></div><div class='line' id='LC277'><br/></div><div class='line' id='LC278'>&nbsp;&nbsp;<span class="c"># resize swap file to 512 MB</span></div><div class='line' id='LC279'>&nbsp;&nbsp;ensureKeyValueShort <span class="s2">&quot;CONF_SWAPSIZE&quot;</span> <span class="s2">&quot;512&quot;</span> <span class="s2">&quot;/etc/dphys-swapfile&quot;</span></div><div class='line' id='LC280'>&nbsp;&nbsp;dphys-swapfile setup</div><div class='line' id='LC281'>&nbsp;&nbsp;dphys-swapfile swapon</div><div class='line' id='LC282'><br/></div><div class='line' id='LC283'>&nbsp;&nbsp;<span class="c"># enable SSL site</span></div><div class='line' id='LC284'>&nbsp;&nbsp;a2ensite default-ssl</div><div class='line' id='LC285'><br/></div><div class='line' id='LC286'>&nbsp;&nbsp;mkdir -p /var/www/owncloud</div><div class='line' id='LC287'>&nbsp;&nbsp;downloadLatestOwncloudRelease</div><div class='line' id='LC288'>&nbsp;&nbsp;cp -r owncloud/* /var/www/owncloud/</div><div class='line' id='LC289'>&nbsp;&nbsp;rm -rf owncloud</div><div class='line' id='LC290'><br/></div><div class='line' id='LC291'>&nbsp;&nbsp;<span class="c"># change group and owner of all /var/www files recursively to www-data</span></div><div class='line' id='LC292'>&nbsp;&nbsp;chown -R www-data:www-data /var/www</div><div class='line' id='LC293'><br/></div><div class='line' id='LC294'>&nbsp;&nbsp;<span class="c"># restart Apache service</span></div><div class='line' id='LC295'>&nbsp;&nbsp;/etc/init.d/apache2 reload</div><div class='line' id='LC296'><br/></div><div class='line' id='LC297'>&nbsp;&nbsp;<span class="c"># enable US UTF-8 locale</span></div><div class='line' id='LC298'>&nbsp;&nbsp;sudo sed -i -e <span class="s2">&quot;s/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/g&quot;</span> /etc/locale.gen</div><div class='line' id='LC299'><br/></div><div class='line' id='LC300'>&nbsp;&nbsp;<span class="c"># finish the script</span></div><div class='line' id='LC301'>&nbsp;&nbsp;<span class="nv">myipaddress</span><span class="o">=</span><span class="k">$(</span>hostname -I | tr -d <span class="s1">&#39; &#39;</span><span class="k">)</span></div><div class='line' id='LC302'>&nbsp;&nbsp;dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --msgbox <span class="s2">&quot;If everything went right, Owncloud should now be available at the URL https://$myipaddress/owncloud. You have to finish the setup by visiting that site. Before that, we are going to reboot the Raspberry.&quot;</span> 20 60    </div><div class='line' id='LC303'>&nbsp;&nbsp;&nbsp;&nbsp;</div><div class='line' id='LC304'>&nbsp;&nbsp;reboot</div><div class='line' id='LC305'><span class="o">}</span></div><div class='line' id='LC306'><br/></div><div class='line' id='LC307'><span class="k">function </span>main_update<span class="o">()</span></div><div class='line' id='LC308'><span class="o">{</span></div><div class='line' id='LC309'>	downloadLatestOwncloudRelease</div><div class='line' id='LC310'>	cp -r owncloud/* /var/www/owncloud/</div><div class='line' id='LC311'>	rm -rf owncloud</div><div class='line' id='LC312'><br/></div><div class='line' id='LC313'>&nbsp;&nbsp;chown -R www-data:www-data /var/www</div><div class='line' id='LC314'><br/></div><div class='line' id='LC315'>&nbsp;&nbsp;&nbsp;&nbsp;dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --msgbox <span class="s2">&quot;Finished upgrading Owncloud instance.&quot;</span> 20 60    </div><div class='line' id='LC316'><span class="o">}</span></div><div class='line' id='LC317'><br/></div><div class='line' id='LC318'><span class="k">function </span>main_updatescript<span class="o">()</span></div><div class='line' id='LC319'><span class="o">{</span></div><div class='line' id='LC320'>&nbsp;&nbsp;<span class="nv">scriptdir</span><span class="o">=</span><span class="s2">&quot;$( cd &quot;</span><span class="k">$(</span> dirname <span class="s2">&quot;${BASH_SOURCE[0]}&quot;</span> <span class="k">)</span><span class="s2">&quot; &amp;&amp; pwd )&quot;</span></div><div class='line' id='LC321'>&nbsp;&nbsp;<span class="nb">pushd</span> <span class="nv">$scriptdir</span></div><div class='line' id='LC322'>&nbsp;&nbsp;<span class="k">if</span> <span class="o">[[</span> ! -d .git <span class="o">]]</span>; <span class="k">then</span></div><div class='line' id='LC323'><span class="k">    </span>dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --msgbox <span class="s2">&quot;Cannot find direcotry &#39;.git&#39;. Please clone the OwncloudPie script via &#39;git clone git://github.com/petrockblog/OwncloudPie.git&#39;&quot;</span> 20 60    </div><div class='line' id='LC324'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="nb">popd</span></div><div class='line' id='LC325'><span class="nb">    </span><span class="k">return</span></div><div class='line' id='LC326'><span class="k">  fi</span></div><div class='line' id='LC327'><span class="k">  </span>git pull</div><div class='line' id='LC328'>&nbsp;&nbsp;<span class="nb">popd</span></div><div class='line' id='LC329'><span class="nb">  </span>dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --msgbox <span class="s2">&quot;Fetched the latest version of the OwncloudPie script. You need to restart the script.&quot;</span> 20 60    </div><div class='line' id='LC330'><span class="o">}</span></div><div class='line' id='LC331'><br/></div><div class='line' id='LC332'><span class="c"># here starts the main script</span></div><div class='line' id='LC333'><br/></div><div class='line' id='LC334'>checkNeededPackages</div><div class='line' id='LC335'><br/></div><div class='line' id='LC336'><span class="k">if</span> <span class="o">[[</span> -f /etc/nginx/sites-available/default <span class="o">]]</span>; <span class="k">then</span></div><div class='line' id='LC337'><span class="k">  </span><span class="nv">__servername</span><span class="o">=</span><span class="k">$(</span>egrep -m 1 <span class="s2">&quot;server_name &quot;</span> /etc/nginx/sites-available/default | sed <span class="s2">&quot;s| ||g&quot;</span><span class="k">)</span></div><div class='line' id='LC338'>&nbsp;&nbsp;<span class="nv">__servername</span><span class="o">=</span><span class="k">${</span><span class="nv">__servername</span><span class="p">:</span><span class="nv">11</span><span class="p">:</span><span class="nv">0</span><span class="p">-1</span><span class="k">}</span></div><div class='line' id='LC339'><span class="k">else</span></div><div class='line' id='LC340'><span class="k">  </span><span class="nv">__servername</span><span class="o">=</span><span class="s2">&quot;url.ofmyserver.com&quot;</span></div><div class='line' id='LC341'><span class="k">fi</span></div><div class='line' id='LC342'><br/></div><div class='line' id='LC343'><span class="k">if</span> <span class="o">[</span> <span class="k">$(</span>id -u<span class="k">)</span> -ne 0 <span class="o">]</span>; <span class="k">then</span></div><div class='line' id='LC344'><span class="k">  </span><span class="nb">printf</span> <span class="s2">&quot;Script must be run as root. Try &#39;sudo ./OwncloudPie_setup&#39;\n&quot;</span></div><div class='line' id='LC345'>&nbsp;&nbsp;<span class="nb">exit </span>1</div><div class='line' id='LC346'><span class="k">fi</span></div><div class='line' id='LC347'><br/></div><div class='line' id='LC348'><span class="k">while </span><span class="nb">true</span>; <span class="k">do</span></div><div class='line' id='LC349'><span class="k">    </span><span class="nv">cmd</span><span class="o">=(</span>dialog --backtitle <span class="s2">&quot;PetRockBlock.com - OwncloudPie Setup.&quot;</span> --menu <span class="s2">&quot;You MUST set the server URL (e.g., 192.168.0.10 or myaddress.dyndns.org) before starting one of the installation routines. Choose task:&quot;</span> 22 76 16<span class="o">)</span></div><div class='line' id='LC350'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="nv">options</span><span class="o">=(</span>1 <span class="s2">&quot;Set server URL ($__servername)&quot;</span></div><div class='line' id='LC351'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2 <span class="s2">&quot;New installation, NGiNX based&quot;</span></div><div class='line' id='LC352'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3 <span class="s2">&quot;New installation, Apache based&quot;</span></div><div class='line' id='LC353'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4 <span class="s2">&quot;Update existing Owncloud installation&quot;</span></div><div class='line' id='LC354'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5 <span class="s2">&quot;Update OwncloudPie script&quot;</span><span class="o">)</span></div><div class='line' id='LC355'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="nv">choice</span><span class="o">=</span><span class="k">$(</span><span class="s2">&quot;${cmd[@]}&quot;</span> <span class="s2">&quot;${options[@]}&quot;</span> 2&gt;&amp;1 &gt;/dev/tty<span class="k">)</span>    </div><div class='line' id='LC356'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">if</span> <span class="o">[</span> <span class="s2">&quot;$choice&quot;</span> !<span class="o">=</span> <span class="s2">&quot;&quot;</span> <span class="o">]</span>; <span class="k">then</span></div><div class='line' id='LC357'><span class="k">        case</span> <span class="nv">$choice</span> in</div><div class='line' id='LC358'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1<span class="o">)</span> main_setservername ;;</div><div class='line' id='LC359'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2<span class="o">)</span> main_newinstall_nginx ;;</div><div class='line' id='LC360'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3<span class="o">)</span> main_newinstall_apache ;;</div><div class='line' id='LC361'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4<span class="o">)</span> main_update ;;</div><div class='line' id='LC362'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5<span class="o">)</span> main_updatescript ;;</div><div class='line' id='LC363'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">esac</span></div><div class='line' id='LC364'><span class="k">    else</span></div><div class='line' id='LC365'><span class="k">        </span><span class="nb">break</span></div><div class='line' id='LC366'><span class="nb">    </span><span class="k">fi</span></div><div class='line' id='LC367'><span class="k">done</span></div><div class='line' id='LC368'>clear</div></pre></div>
          </td>
        </tr>
      </table>
  </div>

          </div>
        </div>

        <a href="#jump-to-line" rel="facebox" data-hotkey="l" class="js-jump-to-line" style="display:none">Jump to Line</a>
        <div id="jump-to-line" style="display:none">
          <h2>Jump to Line</h2>
          <form accept-charset="UTF-8" class="js-jump-to-line-form">
            <input class="textfield js-jump-to-line-field" type="text">
            <div class="full-button">
              <button type="submit" class="button">Go</button>
            </div>
          </form>
        </div>

      </div>
    </div>
</div>

<div id="js-frame-loading-template" class="frame frame-loading large-loading-area" style="display:none;">
  <img class="js-frame-loading-spinner" src="https://a248.e.akamai.net/assets.github.com/images/spinners/octocat-spinner-128.gif?1347543527" height="64" width="64">
</div>


        </div>
      </div>
      <div class="context-overlay"></div>
    </div>

      <div id="footer-push"></div><!-- hack for sticky footer -->
    </div><!-- end of wrapper - hack for sticky footer -->

      <!-- footer -->
      <div id="footer">
  <div class="container clearfix">

      <dl class="footer_nav">
        <dt>GitHub</dt>
        <dd><a href="https://github.com/about">About us</a></dd>
        <dd><a href="https://github.com/blog">Blog</a></dd>
        <dd><a href="https://github.com/contact">Contact &amp; support</a></dd>
        <dd><a href="http://enterprise.github.com/">GitHub Enterprise</a></dd>
        <dd><a href="http://status.github.com/">Site status</a></dd>
      </dl>

      <dl class="footer_nav">
        <dt>Applications</dt>
        <dd><a href="http://mac.github.com/">GitHub for Mac</a></dd>
        <dd><a href="http://windows.github.com/">GitHub for Windows</a></dd>
        <dd><a href="http://eclipse.github.com/">GitHub for Eclipse</a></dd>
        <dd><a href="http://mobile.github.com/">GitHub mobile apps</a></dd>
      </dl>

      <dl class="footer_nav">
        <dt>Services</dt>
        <dd><a href="http://get.gaug.es/">Gauges: Web analytics</a></dd>
        <dd><a href="http://speakerdeck.com">Speaker Deck: Presentations</a></dd>
        <dd><a href="https://gist.github.com">Gist: Code snippets</a></dd>
        <dd><a href="http://jobs.github.com/">Job board</a></dd>
      </dl>

      <dl class="footer_nav">
        <dt>Documentation</dt>
        <dd><a href="http://help.github.com/">GitHub Help</a></dd>
        <dd><a href="http://developer.github.com/">Developer API</a></dd>
        <dd><a href="http://github.github.com/github-flavored-markdown/">GitHub Flavored Markdown</a></dd>
        <dd><a href="http://pages.github.com/">GitHub Pages</a></dd>
      </dl>

      <dl class="footer_nav">
        <dt>More</dt>
        <dd><a href="http://training.github.com/">Training</a></dd>
        <dd><a href="https://github.com/edu">Students &amp; teachers</a></dd>
        <dd><a href="http://shop.github.com">The Shop</a></dd>
        <dd><a href="/plans">Plans &amp; pricing</a></dd>
        <dd><a href="http://octodex.github.com/">The Octodex</a></dd>
      </dl>

      <hr class="footer-divider">


    <p class="right">&copy; 2013 <span title="0.15761s from fe16.rs.github.com">GitHub</span>, Inc. All rights reserved.</p>
    <a class="left" href="https://github.com/">
      <span class="mega-icon mega-icon-invertocat"></span>
    </a>
    <ul id="legal">
        <li><a href="https://github.com/site/terms">Terms of Service</a></li>
        <li><a href="https://github.com/site/privacy">Privacy</a></li>
        <li><a href="https://github.com/security">Security</a></li>
    </ul>

  </div><!-- /.container -->

</div><!-- /.#footer -->


    <div class="fullscreen-overlay js-fullscreen-overlay" id="fullscreen_overlay">
  <div class="fullscreen-container js-fullscreen-container">
    <div class="textarea-wrap">
      <textarea name="fullscreen-contents" id="fullscreen-contents" class="js-fullscreen-contents" placeholder="" data-suggester="fullscreen_suggester"></textarea>
          <div class="suggester-container">
              <div class="suggester fullscreen-suggester js-navigation-container" id="fullscreen_suggester"
                 data-url="/petrockblog/OwncloudPie/suggestions/commit">
              </div>
          </div>
    </div>
  </div>
  <div class="fullscreen-sidebar">
    <a href="#" class="exit-fullscreen js-exit-fullscreen tooltipped leftwards" title="Exit Zen Mode">
      <span class="mega-icon mega-icon-normalscreen"></span>
    </a>
    <a href="#" class="theme-switcher js-theme-switcher tooltipped leftwards"
      title="Switch themes">
      <span class="mini-icon mini-icon-brightness"></span>
    </a>
  </div>
</div>



    <div id="ajax-error-message" class="flash flash-error">
      <span class="mini-icon mini-icon-exclamation"></span>
      Something went wrong with that request. Please try again.
      <a href="#" class="mini-icon mini-icon-remove-close ajax-error-dismiss"></a>
    </div>

    
    
    <span id='server_response_time' data-time='0.15810' data-host='fe16'></span>
    
  </body>
</html>

